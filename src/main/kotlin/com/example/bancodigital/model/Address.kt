package com.example.bancodigital.model

import com.example.bancodigital.converter.LocalDateConverter
import com.example.bancodigital.dto.AddressDTO
import com.example.bancodigital.dto.HolderDTO
import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonIgnore
import java.time.LocalDate
import java.util.*
import javax.persistence.Column
import javax.persistence.Convert
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

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
    val country: String,
    @Convert(converter = LocalDateConverter::class)
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
            country = addressDTO.country,
            dateCreation = savedAddress.get().dateCreation,
            info = "Address updated in ${LocalDate.now()}",
            holder = savedAddress.get().holder
        )
    }


}