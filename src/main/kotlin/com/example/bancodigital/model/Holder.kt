package com.example.bancodigital.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.databind.ser.std.DateSerializer
import org.hibernate.validator.constraints.Length
import java.time.LocalDate
import javax.persistence.Column
import javax.persistence.Embedded
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.validation.constraints.NotNull

@Entity
data class Holder(
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   val id: Long,
   @Length(min=2, max=120)
   @NotNull
   val name: String,
   @Length(min=11, max=11)
   @Column(unique=true, nullable = false)
   val nationalRegistration: String,
   @Column(nullable = false)
   @JsonSerialize(using = DateSerializer::class)
   val birthDate: LocalDate,
   val active: Boolean,
   @Embedded
   val address: Address,
   val info: String,
   @OneToMany(mappedBy = "holder", fetch = FetchType.EAGER)
   @JsonIgnore
   val accounts: List<Account>,
   )