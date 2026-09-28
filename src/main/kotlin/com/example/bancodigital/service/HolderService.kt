package com.example.bancodigital.service

import com.example.bancodigital.dto.CreateHolderDTO
import com.example.bancodigital.dto.UpdateHolderDTO
import com.example.bancodigital.model.Holder
import com.example.bancodigital.repository.HolderRepository
import com.example.bancodigital.util.ValidateBirthDate
import com.example.bancodigital.util.ValidateNationalRegistration
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*


@Service
class HolderService(
    val holderRepository: HolderRepository,
    val validateNationalRegistration: ValidateNationalRegistration,
    val validateBirthDate: ValidateBirthDate
) {

    fun createHolder(holder: Holder): Holder {
        return holderRepository.save(holder)
    }

    fun findById(id: Long): Optional<Holder> {
        return holderRepository.findById(id)
    }

    fun findByExternalKey(externalKey: String) : Holder? {
        return holderRepository.findByExternalKey(UUID.fromString(externalKey))
    }

    fun findAll(): List<Holder> {
        return holderRepository.findAll()
    }

    fun delete(id: Long) {
        /*
            ALTERADO PARA EXCLUSÃO LÓGICA  A USAR A PROPRIEDADE ACTIVE ABAIXO
         */
        //holderRepository.deleteById(id)
    }

    fun updateHolder(id: Long, holderDTO: UpdateHolderDTO): Holder {
        val holder = holderRepository.findById(id)
            .orElseThrow { IllegalArgumentException("Holder with id $id not found") }

        val date = LocalDate.now()
            .format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))

        holder.name = holderDTO.name
        holder.birthDate = holderDTO.birthDate
        holder.info = "Holder updated on this date : $date"

        return holderRepository.save(holder)
    }

    fun updateActiveProperty(id: Long, active: Boolean) {
        val holder = holderRepository.findById(id)
            .orElseThrow { IllegalArgumentException("Holder with id $id not found") }

        holder.active = active

        holderRepository.save(holder)
    }

    fun checkNationalRegistration(nationalRegistration: String) : Boolean {
        val holder = holderRepository.findByNationalRegistration(nationalRegistration)
        return holder != null
    }

    fun validateForUpdate(holder: UpdateHolderDTO): String {
        return if (holder.birthDate.let { validateBirthDate.calculateAge(it, LocalDate.now()) } < 18) {
            "The holder is under eighteen years of age"
        } else {
            ""
        }
    }

    fun validateForCreate(createHolderDTO: CreateHolderDTO): String {
        return if (!validateNationalRegistration.isNationalRegistration(createHolderDTO.nationalRegistration)) {
            "Holder national registration [ ${createHolderDTO.nationalRegistration} ] is invalid"
        } else if (validateBirthDate.calculateAge(createHolderDTO.birthDate, LocalDate.now()) < 18) {
            "The holder is under eighteen years of age"
        } else if (checkNationalRegistration(createHolderDTO.nationalRegistration)) {
            "There is already a registered holder with this national registration"
        } else {
            ""
        }
    }
}