package com.example.bancodigital.facade

import com.example.bancodigital.dto.CreditByBarcode
import com.example.bancodigital.dto.CreditDTO
import com.example.bancodigital.dto.DebitByQrcodeDTO
import com.example.bancodigital.dto.DebitDTO
import com.example.bancodigital.model.BarcodeRegister
import com.example.bancodigital.model.Transaction
import com.example.bancodigital.service.BarcodeService
import com.example.bancodigital.service.QrCodeService
import com.example.bancodigital.service.TransactionService
import com.example.bancodigital.util.BarcodeReader
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.math.BigDecimal
import java.util.*

@Component
class TransactionFacade(
    private val transactionService: TransactionService,
    private val qrCodeService: QrCodeService,
    private val barcodeReader: BarcodeReader,
    private val barcodeService: BarcodeService,
) {

    fun findTransactionsByAccount(id: Long): List<Transaction> {
        return transactionService.findTransactionsByAccount(id)
    }

    fun transactionOfCredit(creditDTO: CreditDTO, externalKey: UUID): String {
        return transactionService.transactionOfCredit(creditDTO, externalKey)
    }

    fun validationsForDebit(externalKey: UUID, value: BigDecimal): String {
        return transactionService.validationsForDebit(externalKey, value)
    }

    fun validationForCredit(accountExternalKey: UUID): Boolean {
        return transactionService.validationForCredit(accountExternalKey)
    }

    fun transactionOfDebit(debitDTO: DebitDTO, externalKey: UUID): String {
        return transactionService.transactionOfDebit(debitDTO, externalKey)
    }

    fun transactionOfDebitByQrcode(debitByQrcodeDTO: DebitByQrcodeDTO): String {
        return transactionService.transactionOfDebitByQrcode(debitByQrcodeDTO)
    }

    fun transactionOfCreditByBarcode(barcodeRegister: BarcodeRegister): String {
        return transactionService.transactionOfCreditByBarcode(barcodeRegister)
    }

    fun readForQrcodeTransaction(file: MultipartFile): DebitByQrcodeDTO {
        return qrCodeService.readForQrcodeTransaction(file)
    }

    fun decodeImageForCredit(file: MultipartFile): CreditByBarcode {
        return barcodeReader.decodeImageForCredit(file)
    }

    fun barcodeRegister(creditByBarcode: CreditByBarcode): BarcodeRegister {
        return barcodeService.register(BarcodeRegister.fromBarcodeRegister(creditByBarcode))
    }

    fun findBarcode(externalKey: String): BarcodeRegister? {
        return barcodeService.findByBarcode(UUID.fromString(externalKey))
    }

    fun findBarcodeByAccount(accountExternalKey: String): List<BarcodeRegister>? {
        return barcodeService.findBarcodeByAccount(UUID.fromString(accountExternalKey))
    }
}