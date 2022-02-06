package com.example.bancodigital.model

import io.swagger.annotations.ApiModelProperty

data class JwtRequest(
    @ApiModelProperty(example = "user")
    val username: String,
    @ApiModelProperty(example = "password")
    val password: String
)