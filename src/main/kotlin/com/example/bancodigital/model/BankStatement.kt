package com.example.bancodigital.model

import com.fasterxml.jackson.annotation.JsonIgnore
import java.time.LocalDate
import java.util.*
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToMany
import jakarta.persistence.CascadeType
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.ForeignKey
import jakarta.persistence.JoinTable
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes

@Entity
data class BankStatement(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(name = "external_key", columnDefinition = "VARCHAR(36)")
    val externalKey: UUID = UUID.randomUUID(),
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(name = "account_external_key", columnDefinition = "VARCHAR(36)")
    val accountExternalKey: UUID,
    val initialDate: LocalDate,
    val finalDate: LocalDate,
    @Enumerated(EnumType.STRING)
    val transactionType: TransactionType,
    @OneToMany(cascade = [CascadeType.ALL])
    @JoinTable(
        name = "bank_statement_transaction",
        joinColumns = [
            JoinColumn(
                name = "bank_statement_id",
                referencedColumnName = "id",
                foreignKey = ForeignKey(name = "fk_bst_bank_statement")
            )
        ],
        inverseJoinColumns = [
            JoinColumn(
                name = "transaction_id",
                referencedColumnName = "id",
                foreignKey = ForeignKey(name = "fk_bst_transaction")
            )
        ]
    )
    @JsonIgnore
    val transactions: List<Transactions> = emptyList()
) {
    companion object {

        fun from(
            accountExternalKey: UUID,
            initialDate: LocalDate,
            finalDate: LocalDate,
            transactionType: TransactionType,
            transactions: List<Transactions>,
        ) = BankStatement(
            accountExternalKey = accountExternalKey,
            initialDate = initialDate,
            finalDate = finalDate,
            transactionType = transactionType,
            transactions = transactions
        )
    }
}