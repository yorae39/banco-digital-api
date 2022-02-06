package com.example.bancodigital.model

import com.example.bancodigital.converter.LocalDateConverter
import java.math.BigDecimal
import java.time.LocalDate
import javax.persistence.Convert
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.validation.constraints.NotNull

@Entity
data class Transaction(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    @NotNull
    val description: String,
    val observation: String,
    val value: BigDecimal,
    @Convert(converter = LocalDateConverter::class)
    val dateTransaction: LocalDate,
    @NotNull
    @Enumerated(EnumType.STRING)
    val transactionType: TransactionType,
    @ManyToOne
    val account: Account,
)