package com.example.bancodigital.dto

import java.math.BigDecimal

data class DebitDTO(
    val description: String,
    val observation: String,
    val value: BigDecimal,
) {
    companion object {
        fun from(debitDTO: DebitDTO) = DebitDTO(
            description = debitDTO.description,
            observation = debitDTO.observation,
            value = debitDTO.value
        )

        fun fromQrcode(debitByQrcodeDTO: DebitByQrcodeDTO) = DebitDTO(
            description = debitByQrcodeDTO.description,
            observation = debitByQrcodeDTO.observation,
            value = debitByQrcodeDTO.value
        )

        fun fromBarcode(debitByBarcode: DebitByBarcode) = DebitDTO(
            description = debitByBarcode.description,
            observation = debitByBarcode.observation,
            value = debitByBarcode.value
        )
    }
}