package com.example.bancodigital.dto

import com.example.bancodigital.model.TransactionType
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
    }
}