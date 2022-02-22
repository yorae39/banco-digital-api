package com.example.bancodigital.model

import com.example.bancodigital.converter.LocalDateConverter
import com.fasterxml.jackson.annotation.JsonIgnore
import org.hibernate.annotations.Type
import java.time.LocalDate
import java.util.*
import javax.persistence.CascadeType
import javax.persistence.Convert
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.OneToMany

@Entity
data class BankStatement(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Type(type = "uuid-char")
    val externalKey: UUID = UUID.randomUUID(),
    @Type(type = "uuid-char")
    val accountExternalKey: UUID,
    @Convert(converter = LocalDateConverter::class)
    val initialDate: LocalDate,
    @Convert(converter = LocalDateConverter::class)
    val finalDate: LocalDate,
    @Enumerated(EnumType.STRING)
    val transactionType: TransactionType,
    @OneToMany(cascade = [CascadeType.ALL])
    @JoinTable(name = "bank_statement_transaction",
        joinColumns = [JoinColumn(name = "bankStatementId", referencedColumnName = "id",
            nullable = false, updatable = false)],
        inverseJoinColumns = [JoinColumn(name = "transactionId", referencedColumnName = "id",
            nullable = false, updatable = false)])
    @JsonIgnore
    val transactions: List<Transaction>,
) {
    companion object {

        fun from(
            accountExternalKey: UUID,
            initialDate: LocalDate,
            finalDate: LocalDate,
            transactionType: TransactionType,
            transactions: List<Transaction>,
        ) = BankStatement(
            accountExternalKey = accountExternalKey,
            initialDate = initialDate,
            finalDate = finalDate,
            transactionType = transactionType,
            transactions = transactions
        )
    }
}