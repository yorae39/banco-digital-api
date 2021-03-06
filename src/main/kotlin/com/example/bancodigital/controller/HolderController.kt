package com.example.bancodigital.controller

import com.example.bancodigital.dto.HolderDTO
import com.example.bancodigital.event.CreateEvent
import com.example.bancodigital.facade.HolderFacade
import com.example.bancodigital.model.Holder
import com.example.bancodigital.model.response.HolderResponse
import io.swagger.annotations.Api
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.util.*
import javax.servlet.http.HttpServletResponse
import javax.validation.Valid

@RestController
@RequestMapping("/holders")
@Api(tags = ["Holder"])
class HolderController(
    val holderFacade: HolderFacade
) : HolderApi {

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = ["/findAll"], method = [RequestMethod.GET], produces = [MediaType.APPLICATION_JSON_VALUE])
    override fun findAll(): List<Holder> {
        return holderFacade.findAll()
    }

    @RequestMapping(value = ["/save"], method = [RequestMethod.POST], produces = [MediaType.APPLICATION_JSON_VALUE])
    override fun createHolder(
        @Valid @RequestBody holder: Holder,
        httpServletResponse: HttpServletResponse,
    ): ResponseEntity<Any> {
        val validate = holderFacade.validateForCreate(holder)
        return if(validate.isNotEmpty()){
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validate)
        } else {
            val savedHolder = holderFacade.createHolder(holder)
            holderFacade.publishEvent(CreateEvent(this, httpServletResponse, savedHolder.id))
            ResponseEntity.status(HttpStatus.CREATED).body(savedHolder)
        }
    }

    @RequestMapping(value = ["/findById/{id}"],
        method = [RequestMethod.GET],
        produces = [MediaType.APPLICATION_JSON_VALUE])
    override fun findById(@PathVariable id: Long): ResponseEntity<Optional<Holder>> {
        val holder = holderFacade.findById(id)
        return if (!holder.isPresent) ResponseEntity.notFound().build() else ResponseEntity.ok(holder)
    }

    @RequestMapping(value = ["/findByExternalKey/{externalKey}"],
        method = [RequestMethod.GET],
        produces = [MediaType.APPLICATION_JSON_VALUE])
    override fun findByExternalKey(@PathVariable externalKey: String): ResponseEntity<Holder?> {
        val holder = holderFacade.findByExternalKey(externalKey)
        return if (holder == null) ResponseEntity.notFound().build() else ResponseEntity.ok(holder)
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = ["/update/{id}"],
        method = [RequestMethod.PUT],
        consumes = [MediaType.APPLICATION_JSON_VALUE])
    override fun update(@PathVariable id: Long, @RequestBody holderDTO: HolderDTO): ResponseEntity<Any> {
        val validate = holderFacade.validateForUpdate(holderDTO)
        return if(validate.isNotEmpty()){
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validate)
        } else {
            val savedHolder = holderFacade.updateHolder(id, holderDTO)
            ResponseEntity.ok(HolderResponse.from(savedHolder))
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(value = ["/update/{id}/{active}"],
        method = [RequestMethod.PUT],
        consumes = [MediaType.ALL_VALUE])
    override fun updateActiveProperty(
        @PathVariable id: Long,
        @PathVariable active: Boolean,
    ): ResponseEntity<String> {
        holderFacade.updateActiveProperty(id, active)
        val info = "Holder id : $id updated property active for $active"
        return ResponseEntity.ok(info)
    }

    //ALTERADO PARA USAR EXCLUS??O L??GICA ACIMA.DEIXO COMO EXEMPLO
    /*@ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = ["/delete/{id}"], method = [RequestMethod.DELETE])
    override fun delete(@PathVariable id: Long): ResponseEntity<String> {
        val actualDate = Date.from(Instant.now())
        val formatter: Format = SimpleDateFormat("yyyy-MM-dd")
        val date = formatter.format(actualDate)
        val info = "Holder id : $id removed  in date $date"
        holderService.delete(id)
        return ResponseEntity.ok(info)
    }*/

}