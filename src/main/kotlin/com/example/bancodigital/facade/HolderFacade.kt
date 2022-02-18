package com.example.bancodigital.facade

import com.example.bancodigital.dto.HolderDTO
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

    fun validateForCreate(holder: Holder): String {
        return holderService.validateForCreate(holder)
    }

    fun createHolder(holder: Holder): Holder {
        return holderService.createHolder(holder)
    }

    fun findById(id: Long): Optional<Holder> {
        return holderService.findById(id)
    }

    fun findByExternalKey(externalKey: String): Holder? {
        return holderService.findByExternalKey(externalKey)
    }

    fun validateForUpdate(holderDTO: HolderDTO): String {
        return holderService.validateForUpdate(holderDTO)
    }

    fun updateHolder(id: Long, holderDTO: HolderDTO): Holder {
        return holderService.updateHolder(id, holderDTO)
    }

    fun updateActiveProperty(id: Long, active: Boolean) {
        holderService.updateActiveProperty(id, active)
    }

    fun publishEvent(createEvent: CreateEvent) {
        publisher.publishEvent(createEvent)
    }

}