package com.example.bancodigital.model

import com.example.bancodigital.dto.CreditByBarcode
import java.math.BigDecimal
import java.util.*
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes

@Entity
data class BarcodeRegister(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(name = "external_key", columnDefinition = "VARCHAR(36)")
    val externalKey: UUID = UUID.randomUUID(),
    @Column(name = "account_external_key", columnDefinition = "VARCHAR(36)", nullable = false)
    val accountExternalKey: UUID,
    val description: String,
    val observation: String,
    val value: BigDecimal
) {
    companion object {
        fun fromBarcodeRegister(creditByBarcode: CreditByBarcode) = BarcodeRegister(
            accountExternalKey = UUID.fromString(creditByBarcode.accountExternalKey),
            description = creditByBarcode.description,
            observation = creditByBarcode.observation,
            value = creditByBarcode.value
        )
    }
}