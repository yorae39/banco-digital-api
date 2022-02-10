package com.example.bancodigital.dto

import com.example.bancodigital.model.TransactionType
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
    }
}