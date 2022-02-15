package com.example.bancodigital.dto

import com.example.bancodigital.model.BarcodeRegister
import java.math.BigDecimal

data class CreditDTO(
    val description: String,
    val observation: String,
    val value: BigDecimal
) {

    companion object {
        fun from(creditDTO: CreditDTO) = CreditDTO(
                description = creditDTO.description,
                observation = creditDTO.observation,
                value = creditDTO.value
            )

        fun fromBarcode(barcodeRegister: BarcodeRegister) = CreditDTO(
            description = barcodeRegister.description,
            observation = barcodeRegister.observation,
            value = barcodeRegister.value
        )
    }
}