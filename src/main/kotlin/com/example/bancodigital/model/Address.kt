package com.example.bancodigital.model

import com.fasterxml.jackson.annotation.JsonBackReference
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity
data class Address(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    val street: String,
    val number: String,
    val complement: String,
    val neighborhood: String,
    val zipCode: String,
    val city: String,
    val country: String,
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference
    val holder: Holder
)