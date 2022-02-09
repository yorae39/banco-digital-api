package com.example.bancodigital.dto

import com.example.bancodigital.model.Holder
import java.time.LocalDate
import java.util.*

class HolderDTO(
    val name: String,
    val birthDate: LocalDate,
) {

    companion object {
        fun from(holder: Optional<Holder>): HolderDTO =
            HolderDTO(
                name = holder.get().name,
                birthDate = holder.get().birthDate
            )
    }
}