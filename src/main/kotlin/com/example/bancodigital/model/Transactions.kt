package com.example.bancodigital.model

import com.example.bancodigital.dto.CreditDTO
import com.example.bancodigital.dto.DebitDTO
import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.Column
import java.math.BigDecimal
import java.time.LocalDate
import java.util.*
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes

@Entity
data class Transactions(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(name = "external_key", columnDefinition = "VARCHAR(36)")
    val externalKey: UUID = UUID.randomUUID(),
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(name = "qrcode_external_key", columnDefinition = "VARCHAR(36)")
    var qrcodeExternalKey: UUID? = null,
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(name = "barcode_external_key", columnDefinition = "VARCHAR(36)")
    var barcodeExternalKey: UUID? = null,
    val description: String,
    val observation: String,
    val value: BigDecimal,
    @JsonFormat(pattern = "dd/MM/yyyy")
    val dateTransaction: LocalDate = LocalDate.now(),
    @Enumerated(EnumType.STRING)
    val transactionType: TransactionType,
    @ManyToOne
    @JoinColumn
    @JsonIgnore
    val account: Account
){
    companion object {
        fun operationOfCredit(creditDTO: CreditDTO, savedAccount: Account) =
          Transactions(
              description = creditDTO.description,
              observation = creditDTO.observation,
              value = creditDTO.value,
              transactionType = TransactionType.CREDIT,
              account = savedAccount
          )

        fun operationOfDebit(debitDTO: DebitDTO, savedAccount: Account) =
            Transactions(
                description = debitDTO.description,
                observation = debitDTO.observation,
                value = debitDTO.value,
                transactionType = TransactionType.DEBIT,
                account = savedAccount
            )
    }
}