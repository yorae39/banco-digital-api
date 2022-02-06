package com.example.bancodigital.controller

import com.example.bancodigital.event.CreateEvent
import com.example.bancodigital.model.Holder
import com.example.bancodigital.service.HolderService
import com.example.bancodigital.util.ValidateBirthDate
import com.example.bancodigital.util.ValidateNationalRegistration
import org.springframework.context.ApplicationEventPublisher
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.text.Format
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.util.*
import javax.servlet.http.HttpServletResponse
import javax.validation.Valid

@RestController
@RequestMapping("/holders")
class HolderController(
    val holderService: HolderService,
    val publisher: ApplicationEventPublisher,
    val validateNationalRegistration: ValidateNationalRegistration,
    val validateBirthDate: ValidateBirthDate
) : HolderApi {

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = ["/findAll"], method = [RequestMethod.GET], produces = [MediaType.APPLICATION_JSON_VALUE])
    override fun findAll(): List<Holder> {
        return holderService.findAll()
    }

    @RequestMapping(value = ["/save"], method = [RequestMethod.POST], produces = [MediaType.APPLICATION_JSON_VALUE])
    override fun createHolder(
        @Valid @RequestBody holder: Holder,
        httpServletResponse: HttpServletResponse,
    ): ResponseEntity<Any> {
        return if (holder.nationalRegistration?.let { validateNationalRegistration.isNationalRegistration(it) } == false){
            val info = "Holder national registration [ ${holder.nationalRegistration} ] is invalid"
            ResponseEntity.ok(info)
        }else if(holder.birthDate?.let { validateBirthDate.calculateAge(it, LocalDate.now()) }!! < 18) {
            val info = "The holder is under eighteen years of age"
            ResponseEntity.ok(info)
        }else if (holder.nationalRegistration?.let { holderService.checkNationalRegistration(it) } == true) {
            val info = "There is already a registered holder with this national registration"
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(info)
        }else {
            val savedHolder = holderService.createHolder(holder)
            publisher.publishEvent(CreateEvent(this, httpServletResponse, savedHolder.id))
            ResponseEntity.status(HttpStatus.CREATED).body(savedHolder)
        }
    }

    @RequestMapping(value = ["/findById/{id}"],
        method = [RequestMethod.GET],
        produces = [MediaType.APPLICATION_JSON_VALUE])
    override fun findById(@PathVariable id: Long): ResponseEntity<Optional<Holder>> {
        val holder = holderService.findById(id)
        return if (!holder.isPresent) ResponseEntity.notFound().build() else ResponseEntity.ok(holder)
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = ["/update/{id}"],
        method = [RequestMethod.PUT],
        consumes = [MediaType.APPLICATION_JSON_VALUE])
    override fun update(@PathVariable id: Long, @RequestBody holder: Holder): ResponseEntity<Holder> {
        val savedHolder = holderService.updateHolder(id, holder)
        return ResponseEntity.ok(savedHolder)
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(value = ["/update/{id}/{active}"],
        method = [RequestMethod.PUT],
        consumes = [MediaType.ALL_VALUE])
    override fun updateActiveProperty(
        @PathVariable id: Long,
        @PathVariable active: Boolean,
    ): ResponseEntity<String> {
        holderService.updateActiveProperty(id, active)
        val info = "Holder id : $id updated active for $active"
        return ResponseEntity.ok(info)
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = ["/delete/{id}"], method = [RequestMethod.DELETE])
    override fun delete(@PathVariable id: Long): ResponseEntity<String> {
        val actualDate = Date.from(Instant.now())
        val formatter: Format = SimpleDateFormat("yyyy-MM-dd")
        val date = formatter.format(actualDate)
        val info = "Holder id : $id removed  in date $date"
        holderService.delete(id)
        return ResponseEntity.ok(info)
    }

}