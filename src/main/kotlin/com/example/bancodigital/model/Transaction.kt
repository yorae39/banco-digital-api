package com.example.bancodigital.model

import com.example.bancodigital.converter.LocalDateConverter
import com.example.bancodigital.dto.CreditDTO
import com.example.bancodigital.dto.DebitDTO
import com.fasterxml.jackson.annotation.JsonIgnore
import org.hibernate.annotations.Type
import java.math.BigDecimal
import java.time.LocalDate
import java.util.*
import javax.persistence.Convert
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity
data class Transaction(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Type(type="uuid-char")
    val externalKey: UUID = UUID.randomUUID(),
    @Type(type="uuid-char")
    var qrcodeExternalKey: UUID? = null,
    @Type(type="uuid-char")
    var barcodeExternalKey: UUID? = null,
    val description: String,
    val observation: String,
    val value: BigDecimal,
    @Convert(converter = LocalDateConverter::class)
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
          Transaction(
              description = creditDTO.description,
              observation = creditDTO.observation,
              value = creditDTO.value,
              transactionType = TransactionType.CREDIT,
              account = savedAccount
          )

        fun operationOfDebit(debitDTO: DebitDTO, savedAccount: Account) =
            Transaction(
                description = debitDTO.description,
                observation = debitDTO.observation,
                value = debitDTO.value,
                transactionType = TransactionType.DEBIT,
                account = savedAccount
            )
    }
}