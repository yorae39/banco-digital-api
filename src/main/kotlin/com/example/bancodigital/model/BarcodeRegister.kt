package com.example.bancodigital.model

import com.example.bancodigital.dto.CreditByBarcode
import org.hibernate.annotations.Type
import java.math.BigDecimal
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class BarcodeRegister(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Type(type="uuid-char")
    val externalKey: UUID = UUID.randomUUID(),
    @Type(type="uuid-char")
    @Column(nullable = false)
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