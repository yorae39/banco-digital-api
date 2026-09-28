package com.example.bancodigital.model.response

import com.example.bancodigital.model.Holder
import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDate
import java.util.UUID

data class HolderResponse(
    val id: Long,
    val externalKey: UUID,
    val name: String,
    val nationalRegistration: String,

    @field:JsonFormat(pattern = "dd/MM/yyyy")
    val birthDate: LocalDate,

    val active: Boolean,

    @field:JsonFormat(pattern = "dd/MM/yyyy")
    val dateCreation: LocalDate,

    val info: String?
) {
    companion object {
        fun from(holder: Holder): HolderResponse =
            HolderResponse(
                id = holder.id,
                externalKey = holder.externalKey,
                name = holder.name,
                nationalRegistration = holder.nationalRegistration,
                birthDate = holder.birthDate,
                active = holder.active,
                dateCreation = holder.dateCreation,
                info = holder.info
            )
    }
}