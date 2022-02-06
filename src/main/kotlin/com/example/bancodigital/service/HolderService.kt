package com.example.bancodigital.service

import com.example.bancodigital.model.Holder
import com.example.bancodigital.repository.HolderRepository
import com.example.bancodigital.util.ValidateNationalRegistration
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.data.domain.Example
import org.springframework.stereotype.Service
import java.text.Format
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.util.*


@Service
class HolderService(
    val holderRepository: HolderRepository,
) {

    fun createHolder(holder: Holder): Holder {
        holder.dateCreation = LocalDate.now()
        return holderRepository.save(holder)
    }

    fun findById(id: Long): Optional<Holder> {
        return holderRepository.findById(id)
    }

    fun findAll(): List<Holder> {
        return holderRepository.findAll()
    }

    fun delete(id: Long) {
        holderRepository.deleteById(id)
    }

    fun updateHolder(id: Long, holder: Holder): Holder {
        val actualDate = Date.from(Instant.now())
        val formatter: Format = SimpleDateFormat("yyyy-MM-dd")
        val date = formatter.format(actualDate)
        val updateHolderInfo = Holder.updateHolder(id, holder, "Holder updated on this date : $date")
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

}