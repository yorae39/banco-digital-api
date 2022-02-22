package com.example.bancodigital.dto

import com.example.bancodigital.model.TransactionType
import java.math.BigDecimal
import java.time.LocalDate
import java.util.UUID

class BankStatementDTO(
    val description: String,
    val value: BigDecimal,
    val initialDate: LocalDate,
    val finalDate: LocalDate,
    val accountExternalKey: UUID,
    val transactionType: TransactionType
)
