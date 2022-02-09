package com.example.bancodigital.service

import com.example.bancodigital.dto.HolderDTO
import com.example.bancodigital.model.Holder
import com.example.bancodigital.repository.HolderRepository
import com.example.bancodigital.util.ValidateBirthDate
import com.example.bancodigital.util.ValidateNationalRegistration
import org.springframework.stereotype.Service
import java.text.Format
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
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

    fun updateHolder(id: Long, holderDTO: HolderDTO): Holder {
        val savedHolder = holderRepository.findById(id)
        val actualDate = Date.from(Instant.now())
        val formatter: Format = SimpleDateFormat("yyyy-MM-dd")
        val date = formatter.format(actualDate)
        val updateHolderInfo = Holder.updateHolder(id, holderDTO, savedHolder, "Holder updated on this date : $date")
        return holderRepository.save(updateHolderInfo)
    }

    fun updateActiveProperty(id: Long, active: Boolean) {
        val savedHolder = holderRepository.findById(id)
        val updateHolderProperty = Holder.updateActiveProperty(id, savedHolder, active)
        holderRepository.save(updateHolderProperty)
    }

    fun checkNationalRegistration(nationalRegistration: String) : Boolean {
        val holder = holderRepository.findByNationalRegistration(nationalRegistration)
        return holder != null
    }

    fun validateForUpdate(holder: HolderDTO): String {
        return if (holder.birthDate.let { validateBirthDate.calculateAge(it, LocalDate.now()) } < 18) {
            "The holder is under eighteen years of age"
        } else {
            ""
        }
    }

    fun validateForCreate(holder: Holder): String {
        return if (!holder.nationalRegistration.let { validateNationalRegistration.isNationalRegistration(it) }){
            "Holder national registration [ ${holder.nationalRegistration} ] is invalid"
        } else if (holder.birthDate.let { validateBirthDate.calculateAge(it, LocalDate.now()) } < 18) {
            "The holder is under eighteen years of age"
        } else if (holder.nationalRegistration.let { checkNationalRegistration(it) }) {
            "There is already a registered holder with this national registration"
        } else {
            ""
        }
    }
}