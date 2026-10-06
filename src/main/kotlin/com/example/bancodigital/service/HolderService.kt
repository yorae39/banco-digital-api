package com.example.bancodigital.service

import com.example.bancodigital.dto.CreateHolderDTO
import com.example.bancodigital.dto.UpdateHolderDTO
import com.example.bancodigital.exception.HolderNotFoundException
import com.example.bancodigital.exception.InvalidExternalKeyException
import com.example.bancodigital.exception.InvalidNationalRegistrationException
import com.example.bancodigital.exception.NationalRegistrationAlreadyExistsException
import com.example.bancodigital.exception.UnderageHolderException
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

    fun createHolder(createHolderDTO: CreateHolderDTO): Holder {
        validateForCreate(createHolderDTO)

        val holder = Holder.from(createHolderDTO)

        return holderRepository.save(holder)
    }

    fun findById(id: Long): Holder {
        return holderRepository.findById(id)
            .orElseThrow { HolderNotFoundException(id) }
    }

    fun findByExternalKey(externalKey: String): Holder {
        val uuid = try {
            UUID.fromString(externalKey)
        } catch (ex: IllegalArgumentException) {
            throw InvalidExternalKeyException(externalKey)
        }

        return holderRepository.findByExternalKey(uuid)
            ?: throw HolderNotFoundException(
                "Holder with externalKey $externalKey not found"
            )
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

    fun updateHolder(
        id: Long,
        holderDTO: UpdateHolderDTO
    ): Holder {

        val holder = holderRepository.findById(id)
            .orElseThrow { HolderNotFoundException(id) }

        validateForUpdate(holderDTO)

        val date = LocalDate.now()
            .format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))

        holder.name = holderDTO.name
        holder.birthDate = holderDTO.birthDate
        holder.info = "Holder updated on this date : $date"

        return holderRepository.save(holder)
    }

    fun updateActiveProperty(id: Long, active: Boolean) {
        val holder = holderRepository.findById(id)
            .orElseThrow {
                HolderNotFoundException(id)
            }

        holder.active = active

        holderRepository.save(holder)
    }

    fun checkNationalRegistration(nationalRegistration: String) : Boolean {
        val holder = holderRepository.findByNationalRegistration(nationalRegistration)
        return holder != null
    }

    private fun validateForUpdate(holderDTO: UpdateHolderDTO) {
        if (
            validateBirthDate.calculateAge(
                holderDTO.birthDate,
                LocalDate.now()
            ) < 18
        ) {
            throw UnderageHolderException()
        }
    }

    private fun validateForCreate(createHolderDTO: CreateHolderDTO) {
        if (!validateNationalRegistration.isNationalRegistration(
                createHolderDTO.nationalRegistration
            )
        ) {
            throw InvalidNationalRegistrationException(
                createHolderDTO.nationalRegistration
            )
        }

        if (
            validateBirthDate.calculateAge(
                createHolderDTO.birthDate,
                LocalDate.now()
            ) < 18
        ) {
            throw UnderageHolderException()
        }

        if (checkNationalRegistration(createHolderDTO.nationalRegistration)) {
            throw NationalRegistrationAlreadyExistsException(
                createHolderDTO.nationalRegistration
            )
        }
    }
}