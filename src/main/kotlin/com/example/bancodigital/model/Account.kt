package com.example.bancodigital.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.databind.ser.std.DateSerializer
import java.time.LocalDate
import javax.enterprise.inject.Default
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.persistence.Temporal
import javax.persistence.TemporalType
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
    @JsonSerialize(using = DateSerializer::class)
    val dateCreation: LocalDate,
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @JsonIgnore
    val accountType: AccountType,
    @ManyToOne(fetch = FetchType.EAGER)
    val holder: Holder,
    @OneToMany(mappedBy = "account")
    val transactions: List<Transaction>,
)