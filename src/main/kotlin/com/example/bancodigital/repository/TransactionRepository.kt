package com.example.bancodigital.repository

import com.example.bancodigital.model.Transactions
import com.example.bancodigital.model.TransactionType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.LocalDate
import java.util.*

interface TransactionRepository : JpaRepository<Transactions, Long> {
    @Query("SELECT t FROM Transactions t WHERE t.account.id = ?1")
    fun findTransactions(id: Long): List<Transactions>

    @Query("SELECT t FROM Transactions t WHERE t.account.externalKey = ?1 AND t.transactionType = ?2 AND " +
            "t.dateTransaction BETWEEN ?3 AND ?4")
    fun findTransactionsByExternalKeyAndTransactionType(
        accountExternalKey: UUID,
        transactionType: TransactionType,
        initialDate: LocalDate,
        finalDate: LocalDate,
    ): List<Transactions>

    fun findByQrcodeExternalKey(qrcodeExternalKey: UUID): Transactions?
    fun findByBarcodeExternalKey(barcodeExternalKey: UUID): Transactions?
}