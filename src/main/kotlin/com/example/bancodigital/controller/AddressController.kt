package com.example.bancodigital.controller

import com.example.bancodigital.dto.AddressDTO
import com.example.bancodigital.facade.AddressFacade
import com.example.bancodigital.model.Address
import com.example.bancodigital.model.response.AddressResponse
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
@RequestMapping("/address")
@Api(tags = ["Address"])
class AddressController(
    val addressFacade: AddressFacade
) : AddressApi {

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = ["/findAll"], method = [RequestMethod.GET], produces = [MediaType.APPLICATION_JSON_VALUE])
    override fun findAll(): List<Address> {
        return addressFacade.findAll()
    }

    @RequestMapping(value = ["/{holderExternalKey}/save"],
        method = [RequestMethod.POST],
        produces = [MediaType.APPLICATION_JSON_VALUE])
    override fun createAddress(
        @PathVariable holderExternalKey: UUID,
        @Valid @RequestBody addressDTO: AddressDTO,
        httpServletResponse: HttpServletResponse,
    ): ResponseEntity<Any> {
        //NÃO IREI FAZER VALIDAÇÕES DO ENDEREÇO A PRINCÍPIO
        val savedHolder = addressFacade.findByHolderExternalKey(holderExternalKey.toString())
        if (savedHolder != null) {
            val address = AddressDTO.from(addressDTO, savedHolder)
            val savedAddress = addressFacade.createAddress(address)
            return ResponseEntity.status(HttpStatus.CREATED).body(savedAddress)
        }
        val info = "Holder externalKey : $holderExternalKey not found"
        return ResponseEntity.ok(info)
    }

    @RequestMapping(value = ["/findById/{id}"],
        method = [RequestMethod.GET],
        produces = [MediaType.APPLICATION_JSON_VALUE])
    override fun findById(@PathVariable id: Long): ResponseEntity<Optional<Address>> {
        val address = addressFacade.findById(id)
        return if (!address.isPresent) ResponseEntity.notFound().build() else ResponseEntity.ok(address)
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = ["/update/{id}"],
        method = [RequestMethod.PATCH],
        consumes = [MediaType.APPLICATION_JSON_VALUE])
    override fun update(@PathVariable id: Long, @RequestBody addressDTO: AddressDTO): ResponseEntity<Any> {
        val savedAddress = addressFacade.findById(id)
        if (savedAddress.get().id != null) {
            val updatedAddress = addressFacade.update(id, addressDTO)
            return ResponseEntity.ok(AddressResponse.from(updatedAddress))
        }
        val info = "Address id : $id not found"
        return ResponseEntity.ok(info)
    }
}