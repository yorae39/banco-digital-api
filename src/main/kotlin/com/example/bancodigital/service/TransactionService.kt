package com.example.bancodigital.service

import com.example.bancodigital.dto.CreditDTO
import com.example.bancodigital.dto.DebitByBarcode
import com.example.bancodigital.dto.DebitByQrcodeDTO
import com.example.bancodigital.dto.DebitDTO
import com.example.bancodigital.model.Account
import com.example.bancodigital.model.AccountType
import com.example.bancodigital.model.Transaction
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

    fun findTransactionsByAccount(id: Long): List<Transaction> {
        val transactions = transactionRepository.findTransactions(id)
        return transactions.ifEmpty { listOf() }
    }

    fun transactionOfCredit(creditDTO: CreditDTO, externalKey: UUID): String {
        val savedAccount = accountRepository.findByExternalKey(externalKey)
        return if (savedAccount != null) {
            val credit = Account.operationOfCredit(creditDTO.value.toLong(), savedAccount)
            accountRepository.save(credit)
            val transaction = Transaction.operationOfCredit(creditDTO, savedAccount)
            transactionRepository.save(transaction)
            "Credit entered successfully : Actual Balance = ${credit.balance}, Description: ${creditDTO.description}"
        } else {
            "Account not found"
        }
    }

    fun transactionOfDebit(debitDTO: DebitDTO, externalKey: UUID): String {
        val account = accountRepository.findByExternalKey(externalKey)
        return savedAccount(account, debitDTO, null, null)
    }

    fun transactionOfDebitByQrcode(debitByQrcodeDTO: DebitByQrcodeDTO): String {
        val transaction = transactionRepository.findByQrcodeExternalKey(UUID.fromString(debitByQrcodeDTO.externalKey))
        if (transaction != null) return "The QR Code has already been used in the system"
        val account = accountRepository.findByExternalKey(UUID.fromString(debitByQrcodeDTO.accountExternalKey))
        val debitDTO = DebitDTO.fromQrcode(debitByQrcodeDTO)
        return savedAccount(account, debitDTO, debitByQrcodeDTO.externalKey, null)
    }

    fun transactionOfDebitByBarcode(debitByBarcode: DebitByBarcode): String {
        val transaction = transactionRepository.findByBarcodeExternalKey(UUID.fromString(debitByBarcode.externalKey))
        if (transaction != null) return "The Barcode has already been used in the system"
        val account = accountRepository.findByExternalKey(UUID.fromString(debitByBarcode.accountExternalKey))
        val debitDTO = DebitDTO.fromBarcode(debitByBarcode)
        return savedAccount(account, debitDTO, null, debitByBarcode.externalKey)
    }

    private fun savedAccount(
        savedAccount: Account?,
        debitDTO: DebitDTO,
        qrcodeExternalKey: String?,
        barcodeExternalKey: String?
    ): String {
        return if (savedAccount != null) {
            savedAccount.balance -= debitDTO.value.toLong()
            val debit = Account.operationOfDebit(savedAccount)
            accountRepository.save(debit)
            val transaction = Transaction.operationOfDebit(debitDTO, savedAccount)
            if (qrcodeExternalKey!= null) transaction.qrcodeExternalKey = UUID.fromString(qrcodeExternalKey)
            if (barcodeExternalKey!= null) transaction.barcodeExternalKey = UUID.fromString(barcodeExternalKey)
            transactionRepository.save(transaction)
            return "Debit entered successfully : Actual Balance = ${debit.balance}, Description = ${debitDTO.description}"
        } else {
            "Account not found"
        }
    }

    fun validations(externalKey: UUID, value: BigDecimal): String {
        val savedAccount = accountRepository.findByExternalKey(externalKey)
        return if (savedAccount != null) {
            return if (savedAccount.balance < value.toLong() && savedAccount.accountType == AccountType.NORMAL) {
                "Debit exceeds account balance limit"
            } else if (savedAccount.balance < -1000 && savedAccount.accountType == AccountType.VIP) {
                "Insufficient balance for this operation"
            } else {
                ""
            }
        } else {
            "Account not found in base"
        }
    }

}