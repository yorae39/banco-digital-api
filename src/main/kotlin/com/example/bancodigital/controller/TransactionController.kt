package com.example.bancodigital.controller

import com.example.bancodigital.dto.BankStatementDTO
import com.example.bancodigital.dto.CreditDTO
import com.example.bancodigital.dto.DebitDTO
import com.example.bancodigital.facade.TransactionFacade
import com.example.bancodigital.model.BankStatement
import com.example.bancodigital.model.BarcodeRegister
import com.example.bancodigital.model.Transactions
import com.example.bancodigital.model.TransactionType
import com.google.zxing.NotFoundException
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import java.util.*

@RestController
@RequestMapping("/transactions")
// @Tag(name = "Transaction") - Coloque assim caso não queira uma interface de documentação com a TransactionAPi
class TransactionController(
    val transactionFacade: TransactionFacade,
) : TransactionAPi {

    @RequestMapping(value = ["/findTransactionsByAccount/{id}"],
        method = [RequestMethod.GET],
        produces = [MediaType.APPLICATION_JSON_VALUE])
    override fun findTransactionsByAccount(@PathVariable id: Long): List<Transactions> {
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

    @RequestMapping(value = ["/credit/scheduled/{externalKey}"],
        method = [RequestMethod.POST],
        produces = [MediaType.APPLICATION_JSON_VALUE])
    fun scheduledCredit(
        @RequestBody creditDTO: CreditDTO,
        @PathVariable externalKey: UUID,
    ): ResponseEntity<String> {
        val result = transactionFacade.transactionOfCreditScheduling(creditDTO, externalKey)
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

    @PutMapping(value = ["/read/qrcode"], consumes = [MULTIPART_FORM_DATA_VALUE])
    @ResponseStatus(value = HttpStatus.OK)
    @Operation(summary = "returns decoded information inside provided QR code")
    @Throws(
        IOException::class,
        NotFoundException::class)
    fun readQrcode(
        @Parameter(description = "Qr-code for read for debit", required = true)
        @RequestPart("file") file: MultipartFile,
    ): ResponseEntity<Any> {
        val debitByQrcodeDTO = transactionFacade.readForQrcodeTransaction(file)

        val validations =
            transactionFacade.validationsForDebit(UUID.fromString(debitByQrcodeDTO.accountExternalKey),
                debitByQrcodeDTO.value)
        return if (validations.isNotEmpty()) {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(validations)
        } else {
            ResponseEntity.ok(transactionFacade.transactionOfDebitByQrcode(debitByQrcodeDTO))
        }
    }

    @PutMapping(value = ["/register/barcode/{accountExternalKey}/{description}/{observation}"],
        consumes = [MULTIPART_FORM_DATA_VALUE])
    @ResponseStatus(value = HttpStatus.OK)
    @Operation(summary = "returns decoded information inside provided Barcode for credit in Account")
    @Throws(
        IOException::class,
        NotFoundException::class)
    fun readBarcodeForRegister(
        @PathVariable accountExternalKey: String,
        @PathVariable description: String,
        @PathVariable observation: String,
        @Parameter(description = "Barcode read for credit", required = true)
        @RequestPart("file") file: MultipartFile,
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
    @Operation(summary = "Consult barcode registered for credit in account by barcode externalKey")
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
    @Operation(summary = "Consult all barcodes registered for account with externalKey")
    fun findBarcodeByAccount(@PathVariable accountExternalKey: String): ResponseEntity<List<BarcodeRegister>> {
        val barcodeRegister = transactionFacade.findBarcodeByAccount(accountExternalKey)
        return ResponseEntity.ok(barcodeRegister)
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = ["/generate/statement"],
        method = [RequestMethod.GET],
        produces = [MediaType.APPLICATION_JSON_VALUE])
    @Operation(summary = "Generate consult of statement by type")
    fun statement(
        accountExternalKey: String,
        transactionType: TransactionType,
        initialDate: String,
        finalDate: String,
        saveConsult: Boolean,
    ): ResponseEntity<List<Transactions>> {
        val transactions = transactionFacade.generateStatement(UUID.fromString(accountExternalKey),
            transactionType, initialDate, finalDate, saveConsult)
        return ResponseEntity.ok(transactions)
    }

    @RequestMapping(value = ["/findAllStatement"],
        method = [RequestMethod.GET],
        produces = [MediaType.APPLICATION_JSON_VALUE])
    @Operation(summary = "Find all statements")
    fun findAllStatements(): List<BankStatement> {
        return transactionFacade.findAllStatements()
    }

    @RequestMapping(value = ["/findStatementByType/{externalKey}"],
        method = [RequestMethod.GET],
        produces = [MediaType.APPLICATION_JSON_VALUE])
    @Operation(summary = "Find saved statement by statement external key")
    fun findStatementsByType(@PathVariable externalKey: UUID): ResponseEntity<List<BankStatementDTO>> {
        return ResponseEntity.ok(transactionFacade.findStatementByExternalKey(externalKey))
    }

}