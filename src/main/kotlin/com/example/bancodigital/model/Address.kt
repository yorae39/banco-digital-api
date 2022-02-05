package com.example.bancodigital.model

import javax.persistence.Embeddable

@Embeddable
data class Address(
    private val street: String,
    private val number: String,
    private val complement: String,
    private val neighborhood: String,
    private val zipCode: String,
    private val city: String,
    private val country: String
)