package com.example.bancodigital.model

import com.example.bancodigital.dto.HolderDTO
import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.hibernate.validator.constraints.Length
import java.time.LocalDate
import java.util.*
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes

@Entity
@JsonIgnoreProperties(value = ["accounts"])
data class Holder(
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   val id: Long,
   @JdbcTypeCode(SqlTypes.VARCHAR)
   @Column(name = "external_key", columnDefinition = "VARCHAR(36)")
   val externalKey: UUID = UUID.randomUUID(),
   @Length(min=2, max=120)
   var name: String,
   @Length(min=11, max=11)
   @Column(unique=true, nullable = false)
   val nationalRegistration: String,
   @JsonFormat(pattern = "dd/MM/yyyy")
   val birthDate: LocalDate,
   val active: Boolean = true,
   @JsonFormat(pattern = "dd/MM/yyyy")
   @Column(nullable = true)
   val dateCreation: LocalDate = LocalDate.now(),
   val info: String
){

   companion object {

      fun updateHolder(id: Long, holderDTO: HolderDTO, savedHolder: Optional<Holder>, info: String) = Holder(
         id = id,
         externalKey = savedHolder.get().externalKey,
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