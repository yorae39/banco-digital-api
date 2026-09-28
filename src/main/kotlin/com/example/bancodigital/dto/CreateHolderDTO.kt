package com.example.bancodigital.dto

import com.fasterxml.jackson.annotation.JsonFormat
import io.swagger.v3.oas.annotations.media.Schema
import org.hibernate.validator.constraints.Length
import java.time.LocalDate

data class CreateHolderDTO(

    @field:Length(min = 2, max = 120)
    val name: String,

    @field:Length(min = 11, max = 11)
    val nationalRegistration: String,

    @field:JsonFormat(pattern = "dd/MM/yyyy")
    @field:Schema(
        type = "string",
        example = "13/07/1974",
        pattern = "dd/MM/yyyy"
    )
    val birthDate: LocalDate,

    val info: String?
)