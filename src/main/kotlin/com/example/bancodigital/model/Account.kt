package com.example.bancodigital.model

import com.example.bancodigital.converter.LocalDateConverter
import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonIgnore
import java.time.LocalDate
import javax.persistence.Column
import javax.persistence.Convert
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.validation.constraints.NotNull

@Entity
data class Account(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    val balance: Double,
    @NotNull
    val active: Boolean,
    @NotNull
    val accountNumber: Long,
    @Convert(converter = LocalDateConverter::class)
    val dateCreation: LocalDate,
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val accountType: AccountType,
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference
    val holder: Holder,
    @OneToMany(mappedBy = "account")
    @Column(nullable = true)
    @JsonIgnore
    val transactions: List<Transaction>,
)