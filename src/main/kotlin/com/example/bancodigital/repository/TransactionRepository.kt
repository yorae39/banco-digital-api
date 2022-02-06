package com.example.bancodigital.repository

import com.example.bancodigital.model.Transaction
import org.springframework.data.jpa.repository.JpaRepository

interface TransactionRepository : JpaRepository<Transaction, Long> {
}