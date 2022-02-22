package com.example.bancodigital.controller

import com.example.bancodigital.dto.BankStatementDTO
import com.example.bancodigital.dto.CreditDTO
import com.example.bancodigital.dto.DebitDTO
import com.example.bancodigital.facade.TransactionFacade
import com.example.bancodigital.model.BankStatement
import com.example.bancodigital.model.BarcodeRegister
import com.example.bancodigital.model.Transaction
import com.example.bancodigital.model.TransactionType
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
    val transactionFacade: TransactionFacade,
) : TransactionAPi {

    @RequestMapping(value = ["/findAll/{id}"],
        method = [RequestMethod.GET],
        produces = [MediaType.APPLICATION_JSON_VALUE])
    override fun findTransactionsByAccount(@PathVariable id: Long): List<Transaction> {
        return transactionFacade.findTransactionsByAccount(id)
    }

    @RequestMapping(value = ["/credit/{externalKey}"],
        method = [RequestMethod.POST],
        produces = [MediaType.APPLICATION_JSON_VALUE])
    override fun creditByAccount(
        @RequestBody creditDTO: CreditDTO,
        @PathVariable externalKey: UUID,
    ): ResponseEntity<String> {
        val result = transactionFacade.transactionOfCredit(creditDTO, externalKey)
        return ResponseEntity.ok(result)
    }

    @RequestMapping(value = ["/debit/{externalKey}"],
        method = [RequestMethod.POST],
        produces = [MediaType.APPLICATION_JSON_VALUE])
    override fun debitByAccount(@RequestBody debitDTO: DebitDTO, @PathVariable externalKey: UUID): ResponseEntity<Any> {
        val validations = transactionFacade.validationsForDebit(externalKey, debitDTO.value)
        return if (validations.isNotEmpty()) {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(validations)
        } else {
            return ResponseEntity.ok(transactionFacade.transactionOfDebit(debitDTO, externalKey))
        }
    }

    @PutMapping(value = ["/read/qrcode"], consumes = ["multipart/form-data"])
    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(value = "returns decoded information inside provided QR code")
    @Throws(
        IOException::class,
        NotFoundException::class)
    fun readQrcode(
        @RequestParam(value = "Qr-code for read for debit", required = true) file: MultipartFile,
    ): ResponseEntity<Any> {
        val debitByQrcodeDTO = transactionFacade.readForQrcodeTransaction(file)

        val validations =
            transactionFacade.validationsForDebit(UUID.fromString(debitByQrcodeDTO.accountExternalKey),
                debitByQrcodeDTO.value)
        return if (validations.isNotEmpty()) {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(validations)
        } else {
            return ResponseEntity.ok(transactionFacade.transactionOfDebitByQrcode(debitByQrcodeDTO))
        }
    }

    @PutMapping(value = ["/register/barcode/{accountExternalKey}/{description}/{observation}"],
        consumes = ["multipart/form-data"])
    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(value = "returns decoded information inside provided Barcode for credit in Account")
    @Throws(
        IOException::class,
        NotFoundException::class)
    fun readBarcodeForRegister(
        @PathVariable accountExternalKey: String,
        @PathVariable description: String,
        @PathVariable observation: String,
        @RequestParam(value = "Barcode read for credit", required = true) file: MultipartFile,
    ): ResponseEntity<BarcodeRegister> {
        val creditByBarcode = transactionFacade.decodeImageForCredit(file)
        creditByBarcode.externalKey = UUID.randomUUID().toString()
        creditByBarcode.accountExternalKey = accountExternalKey
        creditByBarcode.description = description
        creditByBarcode.observation = observation

        return ResponseEntity.ok(transactionFacade.barcodeRegister(creditByBarcode))
    }

    @RequestMapping(value = ["/credit/barcode/{externalKey}"],
        method = [RequestMethod.POST],
        produces = [MediaType.ALL_VALUE])
    @ApiOperation(value = "Consult barcode registered for credit in account by barcode externalKey")
    fun consultBarcodeForCredit(
        @PathVariable externalKey: String,
    ): ResponseEntity<String> {
        val barcode = transactionFacade.findBarcode(externalKey)
        if (barcode != null) {
            val validation = transactionFacade.validationForCredit(barcode.accountExternalKey)
            return if (validation) {
                ResponseEntity.ok(transactionFacade.transactionOfCreditByBarcode(barcode))
            } else {
                ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Account not found")
            }
        }
        return ResponseEntity.ok("Barcode not found")
    }

    @RequestMapping(value = ["/findBarcodeByAccount/{accountExternalKey}"],
        method = [RequestMethod.GET],
        produces = [MediaType.APPLICATION_JSON_VALUE])
    @ApiOperation(value = "Consult all barcodes registered for account with externalKey")
    fun findBarcodeByAccount(@PathVariable accountExternalKey: String): ResponseEntity<List<BarcodeRegister>> {
        val barcodeRegister = transactionFacade.findBarcodeByAccount(accountExternalKey)
        return ResponseEntity.ok(barcodeRegister)
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = ["/generate/statement"],
        method = [RequestMethod.GET],
        produces = [MediaType.APPLICATION_JSON_VALUE])
    @ApiOperation(value = "Generate consult of statement by type")
    fun statement(
        accountExternalKey: String,
        transactionType: TransactionType,
        initialDate: String,
        finalDate: String,
        saveConsult: Boolean
    ): ResponseEntity<List<Transaction>> {
        val transactions = transactionFacade.generateStatement(UUID.fromString(accountExternalKey),
            transactionType, initialDate, finalDate, saveConsult)
        return ResponseEntity.ok(transactions)
    }

    @RequestMapping(value = ["/findAllStatement"],
        method = [RequestMethod.GET],
        produces = [MediaType.APPLICATION_JSON_VALUE])
    @ApiOperation(value = "Find all statements")
    fun findAllStatements(): List<BankStatement> {
        return transactionFacade.findAllStatements()
    }

    @RequestMapping(value = ["/findStatementByType/{externalKey}"],
        method = [RequestMethod.GET],
        produces = [MediaType.APPLICATION_JSON_VALUE])
    @ApiOperation(value = "Find saved statement by statement external key")
    fun findStatementsByType(@PathVariable externalKey: UUID): ResponseEntity<List<BankStatementDTO>> {
        return ResponseEntity.ok(transactionFacade.findStatementByExternalKey(externalKey))
    }

}