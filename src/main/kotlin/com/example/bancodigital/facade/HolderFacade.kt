package com.example.bancodigital.facade

import com.example.bancodigital.dto.CreateHolderDTO
import com.example.bancodigital.dto.UpdateHolderDTO
import com.example.bancodigital.event.CreateEvent
import com.example.bancodigital.model.Holder
import com.example.bancodigital.service.HolderService
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component
import java.util.*

@Component
class HolderFacade(
    private val holderService: HolderService,
    private val publisher: ApplicationEventPublisher
) {

    fun findAll(): List<Holder> {
        return holderService.findAll()
    }

    fun validateForCreate(createHolderDTO: CreateHolderDTO): String {
        return holderService.validateForCreate(createHolderDTO)
    }

    fun createHolder(createHolderDTO: CreateHolderDTO): Holder {
        return holderService.createHolder(Holder.from(createHolderDTO))
    }

    fun findById(id: Long): Optional<Holder> {
        return holderService.findById(id)
    }

    fun findByExternalKey(externalKey: String): Holder? {
        return holderService.findByExternalKey(externalKey)
    }

    fun validateForUpdate(updateHolderDTO: UpdateHolderDTO): String {
        return holderService.validateForUpdate(updateHolderDTO)
    }

    fun updateHolder(id: Long, updateHolderDTO: UpdateHolderDTO): Holder {
        return holderService.updateHolder(id, updateHolderDTO)
    }

    fun updateActiveProperty(id: Long, active: Boolean) {
        holderService.updateActiveProperty(id, active)
    }

    fun publishEvent(createEvent: CreateEvent) {
        publisher.publishEvent(createEvent)
    }

}