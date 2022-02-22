package com.example.bancodigital.model

import com.example.bancodigital.converter.LocalDateConverter
import com.example.bancodigital.dto.HolderDTO
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.hibernate.annotations.Type
import org.hibernate.validator.constraints.Length
import java.time.LocalDate
import java.util.*
import javax.persistence.Column
import javax.persistence.Convert
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
@JsonIgnoreProperties(value = ["accounts"])
data class Holder(
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   val id: Long,
   @Type(type="uuid-char")
   val externalKey: UUID = UUID.randomUUID(),
   @Length(min=2, max=120)
   var name: String,
   @Length(min=11, max=11)
   @Column(unique=true, nullable = false)
   val nationalRegistration: String,
   @Convert(converter = LocalDateConverter::class)
   val birthDate: LocalDate,
   val active: Boolean = true,
   @Convert(converter = LocalDateConverter::class)
   @Column(nullable = true)
   val dateCreation: LocalDate = LocalDate.now(),
   val info: String
){

   companion object {

      fun updateHolder(id: Long, holderDTO: HolderDTO, savedHolder: Optional<Holder>, info: String) = Holder(
         id = id,
         externalKey =savedHolder.get().externalKey,
         name = holderDTO.name,
         nationalRegistration = savedHolder.get().nationalRegistration,
         active = savedHolder.get().active,
         dateCreation = savedHolder.get().dateCreation,
         birthDate = holderDTO.birthDate,
         info = info
      )

      fun updateActiveProperty(id: Long, holder: Optional<Holder>, active: Boolean) = Holder(
         id = id,
         name = holder.get().name,
         nationalRegistration = holder.get().nationalRegistration,
         active = active,
         dateCreation = holder.get().dateCreation,
         birthDate = holder.get().birthDate,
         info = holder.get().info
      )
   }
}