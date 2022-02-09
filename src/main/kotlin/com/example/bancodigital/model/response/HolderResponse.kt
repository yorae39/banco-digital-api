package com.example.bancodigital.model.response

import com.example.bancodigital.model.Holder
import java.time.LocalDate
import java.util.*

class HolderResponse(
    val id: Long,
    val externalKey: UUID,
    val name: String,
    val nationalRegistration: String,
    val birthDate: LocalDate,
    val active: Boolean,
    val dateCreation: LocalDate,
    val info: String
) {
    override fun toString(): String {
        return "HolderResponse(id=$id, externalKey=$externalKey, name=$name, nationalRegistration=$nationalRegistration, birthDate=$birthDate, " +
                "active=$active, dateCreation=$dateCreation, info=$info)"
    }
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