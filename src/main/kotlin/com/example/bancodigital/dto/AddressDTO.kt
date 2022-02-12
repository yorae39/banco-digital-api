package com.example.bancodigital.dto

import com.example.bancodigital.model.Address
import com.example.bancodigital.model.Holder

class AddressDTO(
    val street: String,
    val number: String,
    val complement: String,
    val neighborhood: String,
    val zipCode: String,
    val city: String,
    val country: String
) {
    companion object {
        fun from(addressDTO: AddressDTO, holder: Holder) = Address(
            street = addressDTO.street,
            number = addressDTO.number,
            complement = addressDTO.complement,
            neighborhood = addressDTO.neighborhood,
            zipCode = addressDTO.zipCode,
            city = addressDTO.city,
            country = addressDTO.country,
            info = "Address created for holder: ${holder.name}",
            holder = holder
        )
    }
}