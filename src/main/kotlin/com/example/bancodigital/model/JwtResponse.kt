package com.example.bancodigital.model

import kotlinx.serialization.Serializable

@Serializable
data class JwtResponse(
    val jwtToken: String
)