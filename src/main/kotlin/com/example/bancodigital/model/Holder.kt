package com.example.bancodigital.model

import com.example.bancodigital.dto.CreateHolderDTO
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
   var birthDate: LocalDate,
   var active: Boolean = true,
   @JsonFormat(pattern = "dd/MM/yyyy")
   @Column(nullable = true)
   val dateCreation: LocalDate = LocalDate.now(),
   var info: String
){

   companion object {

      fun from(createHolderDTO: CreateHolderDTO) = Holder(
         id = 0,
         name = createHolderDTO.name,
         nationalRegistration = createHolderDTO.nationalRegistration,
         birthDate = createHolderDTO.birthDate,
         info = createHolderDTO.info
      )
   }
}