package com.example.bancodigital.repository

import com.example.bancodigital.model.Account
import com.example.bancodigital.model.Transaction
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface TransactionRepository : JpaRepository<Transaction, Long> {
    @Query("SELECT t FROM Transaction t WHERE t.account.id = ?1")
    fun findTransactions(id: Long): List<Transaction>
    fun findByQrcodeExternalKey(qrcodeExternalKey: UUID): Transaction?
    fun findByBarcodeExternalKey(barcodeExternalKey: UUID): Transaction?
}