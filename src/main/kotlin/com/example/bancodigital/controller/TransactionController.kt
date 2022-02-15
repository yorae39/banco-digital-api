package com.example.bancodigital.controller

import com.example.bancodigital.dto.CreditDTO
import com.example.bancodigital.dto.DebitDTO
import com.example.bancodigital.model.Transaction
import com.example.bancodigital.service.QrCodeService
import com.example.bancodigital.service.TransactionService
import com.google.zxing.NotFoundException
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import java.util.*

@RestController
@RequestMapping("/transactions")
@Api(tags = ["Transaction"])
class TransactionController(
    val transactionService: TransactionService,
    val qrCodeService: QrCodeService
): TransactionAPi {

    @RequestMapping(value = ["/findAll/{id}"],
        method = [RequestMethod.GET],
        produces = [MediaType.APPLICATION_JSON_VALUE])
    override fun findTransactionsByAccount(@PathVariable id: Long): List<Transaction> {
        return transactionService.findTransactionsByAccount(id)
    }

    @RequestMapping(value = ["/credit/{externalKey}"],
        method = [RequestMethod.POST],
        produces = [MediaType.APPLICATION_JSON_VALUE])
    override fun creditByAccount(@RequestBody creditDTO: CreditDTO, @PathVariable externalKey: UUID): ResponseEntity<String> {
        val result = transactionService.transactionOfCredit(creditDTO, externalKey)
        return ResponseEntity.ok(result)
    }

    @RequestMapping(value = ["/debit/{externalKey}"],
        method = [RequestMethod.POST],
        produces = [MediaType.APPLICATION_JSON_VALUE])
    override fun debitByAccount(@RequestBody debitDTO: DebitDTO, @PathVariable externalKey: UUID): ResponseEntity<Any> {
        val validations = transactionService.validations(externalKey, debitDTO.value)
        return if (validations.isNotEmpty()) {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(validations)
        } else {
            return ResponseEntity.ok(transactionService.transactionOfDebit(debitDTO, externalKey))
        }
    }

    @PutMapping(value = ["/read/qrcode"], consumes = ["multipart/form-data"])
    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(value = "returns decoded information inside provided QR code")
    @Throws(
        IOException::class,
        NotFoundException::class)
    fun readQrcode(@RequestParam(value = "Qr-code for read for debit",  required = true) file: MultipartFile,
    ): ResponseEntity<Any> {
        val debitByQrcodeDTO = qrCodeService.readForQrcodeTransaction(file)

        val validations = transactionService.validations(UUID.fromString(debitByQrcodeDTO.accountExternalKey), debitByQrcodeDTO.value)
        return if (validations.isNotEmpty()) {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(validations)
        } else {
            return ResponseEntity.ok(transactionService.transactionOfDebitByQrcode(debitByQrcodeDTO))
        }
    }

}