package com.example.bancodigital.model

import com.fasterxml.jackson.annotation.JsonIgnore
import java.time.LocalDate
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
    @ManyToOne(fetch = FetchType.EAGER)
    val holder: Holder,
    @OneToMany(mappedBy = "account")
    val transactions: List<Transaction>,
    val active: Boolean,
    val type: String,
    @Temporal(TemporalType.TIMESTAMP)
    val dataCreation: LocalDate,
    @NotNull
    @Enumerated(EnumType.STRING)
    @JsonIgnore
    val accountType: AccountType
)