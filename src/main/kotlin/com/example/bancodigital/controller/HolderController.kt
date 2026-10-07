package com.example.bancodigital.controller

import com.example.bancodigital.dto.CreateHolderDTO
import com.example.bancodigital.dto.UpdateHolderDTO
import com.example.bancodigital.facade.HolderFacade
import com.example.bancodigital.model.response.HolderResponse
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import jakarta.validation.Valid
import org.springframework.web.servlet.support.ServletUriComponentsBuilder

@RestController
@RequestMapping("/holders")
//@Tag(name = "Holder")
class HolderController(
    val holderFacade: HolderFacade
) : HolderApi {

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = ["/findAll"], method = [RequestMethod.GET], produces = [MediaType.APPLICATION_JSON_VALUE])
    override fun findAll(): List<HolderResponse> {
        return holderFacade.findAll()
            .map { HolderResponse.from(it) }
    }

    @RequestMapping(value = ["/save"], method = [RequestMethod.POST], produces = [MediaType.APPLICATION_JSON_VALUE])
    override fun createHolder(
        @Valid @RequestBody createHolderDTO: CreateHolderDTO,
    ): ResponseEntity<Any> {

        val savedHolder = holderFacade.createHolder(createHolderDTO)

        val location = ServletUriComponentsBuilder
            .fromCurrentContextPath()
            .path("/holders/findById/{id}")
            .buildAndExpand(savedHolder.id)
            .toUri()

        return ResponseEntity
            .created(location)
            .body(HolderResponse.from(savedHolder))
    }

    @RequestMapping(value = ["/findById/{id}"],
        method = [RequestMethod.GET],
        produces = [MediaType.APPLICATION_JSON_VALUE])
    override fun findById(
        @PathVariable id: Long
    ): ResponseEntity<HolderResponse> {

        val holder = holderFacade.findById(id)

        return ResponseEntity.ok(
            HolderResponse.from(holder)
        )
    }

    @RequestMapping(value = ["/findByExternalKey/{externalKey}"],
        method = [RequestMethod.GET],
        produces = [MediaType.APPLICATION_JSON_VALUE])
    override fun findByExternalKey(
        @PathVariable externalKey: String
    ): ResponseEntity<HolderResponse> {

        val holder = holderFacade.findByExternalKey(externalKey)

        return ResponseEntity.ok(
            HolderResponse.from(holder)
        )
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(
        value = ["/update/{id}"],
        method = [RequestMethod.PUT],
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    override fun update(
        @PathVariable id: Long,
        @RequestBody updateHolderDTO: UpdateHolderDTO
    ): ResponseEntity<Any> {

        val savedHolder = holderFacade.updateHolder(id, updateHolderDTO)

        return ResponseEntity.ok(HolderResponse.from(savedHolder))
    }

    @RequestMapping(
        value = ["/update/{id}/{active}"],
        method = [RequestMethod.PUT],
        produces = [MediaType.TEXT_PLAIN_VALUE]
    )
    override fun updateActiveProperty(
        @PathVariable id: Long,
        @PathVariable active: Boolean,
    ): ResponseEntity<String> {
        holderFacade.updateActiveProperty(id, active)
        val info = "Holder id : $id updated property active for $active"
        return ResponseEntity.ok(info)
    }

    //ALTERADO PARA USAR EXCLUSÃO LÓGICA ACIMA.DEIXO COMO EXEMPLO
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