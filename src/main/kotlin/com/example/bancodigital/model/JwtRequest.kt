package com.example.bancodigital.model

import io.swagger.v3.oas.annotations.media.Schema

data class JwtRequest(
    @field:Schema(example = "user")
    val username: String,

    @field:Schema(example = "password")
    val password: String
)