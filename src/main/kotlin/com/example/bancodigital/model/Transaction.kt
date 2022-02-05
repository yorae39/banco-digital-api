package com.example.bancodigital.model

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

@Entity
data class Transaction(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    @ManyToOne
    val account: Account,
    val value: Double,
    @Temporal(TemporalType.TIMESTAMP)
    val dateTransaction: LocalDate,
    @Enumerated(EnumType.STRING)
    val typeTransaction: TypeTransaction
)