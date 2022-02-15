package com.example.bancodigital.dto

import java.math.BigDecimal
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

class DebitByBarcode(

    @NotBlank(message = "title must not be empty")
    @Size(max = 50, message = "Title should not exceed more than 50 characters")
    val title: String = "",

    @NotBlank(message = "Qrcode external key")
    @Size(max = 50, message = "Qrcode external key should not exceed more than 50 characters")
    val externalKey: String = "",

    @NotBlank(message = "Account external key")
    @Size(max = 50, message = "Account external key should not exceed more than 50 characters")
    val accountExternalKey: String = "",

    @NotBlank(message = "observation for payment")
    @Size(max = 5000, message = "Observation should not exceed more than 5000 characters")
    val observation: String = "",

    @NotBlank(message = "value of operation must not be empty")
    val value: BigDecimal = BigDecimal(0),

    @NotBlank(message = "description must not be empty")
    val description: String = "",
)