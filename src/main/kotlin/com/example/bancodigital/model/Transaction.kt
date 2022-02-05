package com.example.bancodigital.model

import java.math.BigDecimal
import java.time.LocalDate
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.Temporal
import javax.persistence.TemporalType
import javax.validation.constraints.NotNull

@Entity
data class Transaction(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    @NotNull
    val description: String,
    val observation: String,
    @ManyToOne
    val account: Account,
    val value: BigDecimal,
    @Temporal(TemporalType.TIMESTAMP)
    val dateTransaction: LocalDate,
    @NotNull
    @Enumerated(EnumType.STRING)
    val transaction: TransactionType
)