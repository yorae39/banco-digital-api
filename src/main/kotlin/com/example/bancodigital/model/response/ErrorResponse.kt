package com.example.bancodigital.model.response

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime

data class ErrorResponse(
    val status: Int,
    val error: String,
    val message: String,
    @field:JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    val timestamp: LocalDateTime = LocalDateTime.now()
)