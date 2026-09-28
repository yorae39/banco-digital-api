package com.example.bancodigital.dto

import com.example.bancodigital.model.Holder
import com.fasterxml.jackson.annotation.JsonFormat
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDate
import java.util.*

data class UpdateHolderDTO(
    val name: String,
    @field:JsonFormat(pattern = "dd/MM/yyyy")
    @field:Schema(
        type = "string",
        example = "13/07/1974",
        pattern = "dd/MM/yyyy"
    )
    val birthDate: LocalDate,
) {

    companion object {
        fun from(holder: Optional<Holder>): UpdateHolderDTO =
            UpdateHolderDTO(
                name = holder.get().name,
                birthDate = holder.get().birthDate
            )
    }
}