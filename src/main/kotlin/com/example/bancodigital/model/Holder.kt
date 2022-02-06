package com.example.bancodigital.model

import com.example.bancodigital.converter.LocalDateConverter
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonManagedReference
import org.hibernate.validator.constraints.Length
import java.time.LocalDate
import java.util.*
import javax.persistence.Column
import javax.persistence.Convert
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
   val name: String?,
   @Length(min=11, max=11)
   @Column(unique=true, nullable = false)
   val nationalRegistration: String?,
   @Column(nullable = false)
   @Convert(converter = LocalDateConverter::class)
   val birthDate: LocalDate?,
   val active: Boolean?,
   @JsonIgnore
   @Convert(converter = LocalDateConverter::class)
   var dateCreation: LocalDate?,
   val info: String?,
   @OneToMany(mappedBy = "holder")
   @JsonManagedReference
   @Column(nullable = true)
   val accounts: List<Account>?,
   @OneToMany(mappedBy = "holder", fetch = FetchType.EAGER)
   @Column(nullable = true)
   @JsonManagedReference
   val address: List<Address>?
){

   companion object {

      fun updateHolder(id: Long, holder: Holder, info: String) = Holder(
         id = id,
         name = holder.name,
         nationalRegistration = holder.nationalRegistration,
         active = holder.active,
         dateCreation = holder.dateCreation,
         birthDate = holder.birthDate,
         info = info,
         accounts = holder.accounts,
         address = holder.address
      )

      fun updateActiveProperty(id: Long, holder: Optional<Holder>, active: Boolean) = Holder(
         id = id,
         name = holder.get().name,
         nationalRegistration = holder.get().nationalRegistration,
         active = active,
         dateCreation = holder.get().dateCreation,
         birthDate = holder.get().birthDate,
         info = holder.get().info,
         accounts = holder.get().accounts,
         address = holder.get().address
      )
   }


}