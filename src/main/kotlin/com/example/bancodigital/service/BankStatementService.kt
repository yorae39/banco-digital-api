package com.example.bancodigital.service

import com.example.bancodigital.dto.BankStatementDTO
import com.example.bancodigital.model.BankStatement
import com.example.bancodigital.model.Transaction
import com.example.bancodigital.model.TransactionType
import com.example.bancodigital.repository.BankStatementRepository
import com.example.bancodigital.repository.TransactionRepository
import com.example.bancodigital.util.ParseStringToLocalDate
import org.springframework.stereotype.Service
import java.util.*

@Service
class BankStatementService(
    val transactionRepository: TransactionRepository,
    val bankStatementRepository: BankStatementRepository,
    val parseStringToLocalDate: ParseStringToLocalDate,
) {

    fun generate(
        accountExternalKey: UUID,
        transactionType: TransactionType,
        initialDate: String,
        finalDate: String,
        saveConsult: Boolean
    ): List<Transaction> {
        val initial = parseStringToLocalDate.parse(initialDate)
        val final = parseStringToLocalDate.parse(finalDate)
        val transactions = transactionRepository.findTransactionsByExternalKeyAndTransactionType(accountExternalKey,
            transactionType, initial, final)
        if (saveConsult) saveBankStatement(BankStatement.from(accountExternalKey, initial, final, transactionType, transactions))
        return transactions
    }

    fun findStatement(externalKey: UUID): List<BankStatementDTO> {
        return bankStatementRepository.findSavedStatement(externalKey)
    }

    fun findAll(): List<BankStatement> {
        return bankStatementRepository.findAll()
    }

    private fun saveBankStatement(bankStatement: BankStatement ) {
        bankStatementRepository.save(bankStatement)
    }

}