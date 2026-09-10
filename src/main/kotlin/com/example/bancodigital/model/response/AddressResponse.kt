package com.example.bancodigital.model.response

import com.example.bancodigital.model.Account
import com.example.bancodigital.model.Address
import com.example.bancodigital.model.Holder

class AddressResponse(
    val street: String,
    val number: String,
    val complement: String,
    val neighborhood: String,
    val zipCode: String,
    val city: String,
    val state: String,
    val info: String,
    val holder: Holder
) {
    override fun toString(): String {
        return "AddressResponse(street=$street, number=$number, complement=$complement, neighborhood=$neighborhood, " +
                "zipCode=$zipCode, city=$city, state=$state, info=$info)"
    }
    companion object {

        fun from(address: Address) = AddressResponse(
                street = address.street,
                number = address.number,
                complement = address.complement,
                neighborhood = address.neighborhood,
                zipCode = address.zipCode,
                city = address.city,
                state = address.state,
                info = address.info,
                holder = address.holder
            )
    }
}