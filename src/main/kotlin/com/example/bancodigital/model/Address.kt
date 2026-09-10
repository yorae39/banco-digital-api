package com.example.bancodigital.model

import com.example.bancodigital.dto.AddressDTO
import com.fasterxml.jackson.annotation.JsonIgnore
import java.time.LocalDate
import java.util.*
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

@Entity
data class Address(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val street: String,
    val number: String,
    val complement: String,
    val neighborhood: String,
    val zipCode: String,
    val city: String,
    val state: String,
    @Column(nullable = true)
    var dateCreation: LocalDate = LocalDate.now(),
    @JsonIgnore
    var info: String,
    @ManyToOne
    @JoinColumn
    val holder: Holder
){

    companion object{

        fun updateAddress(id: Long, addressDTO: AddressDTO, savedAddress: Optional<Address>) = Address(
            id = id,
            street = addressDTO.street,
            number = addressDTO.number,
            complement = addressDTO.complement,
            neighborhood = addressDTO.neighborhood,
            zipCode = addressDTO.zipCode,
            city = addressDTO.city,
            state = addressDTO.state,
            dateCreation = savedAddress.get().dateCreation,
            info = "Address updated in ${LocalDate.now()}",
            holder = savedAddress.get().holder
        )
    }

}