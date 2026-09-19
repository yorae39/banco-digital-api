package com.example.bancodigital.service

import com.example.bancodigital.dto.CreditDTO
import com.example.bancodigital.dto.DebitByQrcodeDTO
import com.example.bancodigital.dto.DebitDTO
import com.example.bancodigital.log.logger
import com.example.bancodigital.model.Account
import com.example.bancodigital.model.AccountType
import com.example.bancodigital.model.BarcodeRegister
import com.example.bancodigital.model.Transactions
import com.example.bancodigital.repository.AccountRepository
import com.example.bancodigital.repository.TransactionRepository
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.util.*


@Service
class TransactionService(
    val transactionRepository: TransactionRepository,
    val accountRepository: AccountRepository,
) {

    fun findTransactionsByAccount(id: Long): List<Transactions> {
        val transactions = transactionRepository.findTransactions(id)
        return transactions.ifEmpty { listOf() }
    }

    fun transactionOfCredit(creditDTO: CreditDTO, externalKey: UUID): String {
        val savedAccount = accountRepository.findByExternalKey(externalKey)
        return if (savedAccount != null && savedAccount.active) {
            savedAccount.credit(creditDTO.value.toLong())
            accountRepository.save(savedAccount)
            val transaction = Transactions.operationOfCredit(creditDTO, savedAccount)
            transactionRepository.save(transaction)
            "Credit entered successfully : Actual Balance = ${savedAccount.balance}, Description: ${creditDTO.description}"
        } else {
            "Account not found or inactive"
        }
    }

    fun transactionOfCreditScheduled(creditDTO: CreditDTO, externalKey: UUID): String {
        val periodConclusion: Long = 60000
        var message: String
        val savedAccount = accountRepository.findByExternalKey(externalKey)
        if (savedAccount != null && savedAccount.active) {
            savedAccount.credit(creditDTO.value.toLong())
            message = "Check the balance after one minute for effectiveness"
            Timer().schedule(object : TimerTask() {
                override fun run() {
                    accountRepository.save(savedAccount)
                    logger().trace("Credit entered successfully : Actual Balance = ${savedAccount.balance}, Description: ${creditDTO.description}")
                }
            }, periodConclusion)
        } else {
            message = "Account not found or inactive"
        }
        return message
    }

    fun transactionOfDebit(debitDTO: DebitDTO, externalKey: UUID): String {
        val account = accountRepository.findByExternalKey(externalKey)
        return savedAccountForDebit(account, debitDTO, null)
    }

    fun transactionOfDebitByQrcode(debitByQrcodeDTO: DebitByQrcodeDTO): String {
        val transaction = transactionRepository.findByQrcodeExternalKey(UUID.fromString(debitByQrcodeDTO.externalKey))
        if (transaction != null) return "The QR Code has already been used in the system"
        val account = accountRepository.findByExternalKey(UUID.fromString(debitByQrcodeDTO.accountExternalKey))
        val debitDTO = DebitDTO.fromQrcode(debitByQrcodeDTO)
        return savedAccountForDebit(account, debitDTO, debitByQrcodeDTO.externalKey)
    }

    fun transactionOfCreditByBarcode(barcodeRegister: BarcodeRegister): String {
        val transaction = transactionRepository.findByBarcodeExternalKey(barcodeRegister.externalKey)
        if (transaction != null) return "The Barcode has already been used in the system"
        val account = accountRepository
            .findByExternalKey(UUID.fromString(barcodeRegister.accountExternalKey))
        val creditDTO = CreditDTO.fromBarcode(barcodeRegister)
        return savedAccountForCredit(account, creditDTO, barcodeRegister.externalKey)
    }

    private fun savedAccountForDebit(
        savedAccount: Account?,
        debitDTO: DebitDTO,
        qrcodeExternalKey: String?,
    ): String {
        return if (savedAccount != null && savedAccount.active) {
            savedAccount.debit(debitDTO.value.toLong())
            accountRepository.save(savedAccount)
            val transaction = Transactions.operationOfDebit(debitDTO, savedAccount)
            if (qrcodeExternalKey != null) transaction.qrcodeExternalKey = UUID.fromString(qrcodeExternalKey)
            transactionRepository.save(transaction)
            "Debit entered successfully : Actual Balance = ${savedAccount.balance}, Description = ${debitDTO.description}"
        } else {
            "Account not found or inactive"
        }
    }

    private fun savedAccountForCredit(
        savedAccount: Account?,
        creditDTO: CreditDTO,
        barcodeExternalKey: UUID,
    ): String {
        return if (savedAccount != null && savedAccount.active) {
            savedAccount.credit(creditDTO.value.toLong())
            accountRepository.save(savedAccount)
            val transaction = Transactions.operationOfCredit(creditDTO, savedAccount)
            transaction.barcodeExternalKey = barcodeExternalKey
            transactionRepository.save(transaction)
            "Credit entered successfully : Actual Balance = ${savedAccount.balance}, Description = ${creditDTO.description}"
        } else {
            "Account not found or inactive"
        }
    }

    fun validationsForDebit(externalKey: UUID, value: BigDecimal): String {
        val savedAccount = accountRepository.findByExternalKey(externalKey)
        return if (savedAccount != null && savedAccount.active) {
            if (savedAccount.balance < value.toLong() && savedAccount.accountType == AccountType.NORMAL) {
                "Debit exceeds account balance limit"
            } else if (savedAccount.balance < -1000 && savedAccount.accountType == AccountType.VIP) {
                "Insufficient balance for this operation"
            } else {
                ""
            }
        } else {
            "Account not found or inactive in base"
        }
    }

    fun validationForCredit(externalKey: UUID): Boolean {
        val savedAccount = accountRepository.findByExternalKey(externalKey)
        return savedAccount != null
    }

}