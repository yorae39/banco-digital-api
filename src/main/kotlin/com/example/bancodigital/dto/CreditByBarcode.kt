package com.example.bancodigital.dto

import java.math.BigDecimal
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

class CreditByBarcode(

    @NotBlank(message = "value of operation must not be empty")
    var value: BigDecimal = BigDecimal(0),

    @NotBlank(message = "Barcode external key")
    @Size(max = 255, message = "Barcode external key should not exceed more than 255 characters")
    var externalKey: String = "",

    @NotBlank(message = "Account external key")
    @Size(max = 255, message = "Account external key should not exceed more than 255 characters")
    var accountExternalKey: String = "",

    var observation: String = "",

    var description: String = "",
)