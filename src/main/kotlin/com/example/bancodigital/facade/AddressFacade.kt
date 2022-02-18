package com.example.bancodigital.facade

import com.example.bancodigital.dto.AddressDTO
import com.example.bancodigital.model.Address
import com.example.bancodigital.model.Holder
import com.example.bancodigital.service.AddressService
import com.example.bancodigital.service.HolderService
import org.springframework.stereotype.Component
import java.util.*

@Component
class AddressFacade(
    private val addressService: AddressService,
    private val holderService: HolderService
) {

    fun findAll(): List<Address> {
        return addressService.findAll()
    }

    fun createAddress(address: Address): Address {
        return addressService.createAddress(address)
    }

    fun findById(id: Long): Optional<Address> {
        return addressService.findById(id)
    }

    fun update(id: Long, addressDTO: AddressDTO): Address {
        return addressService.update(id, addressDTO)
    }

    fun findByHolderExternalKey(externalKey: String): Holder? {
        return holderService.findByExternalKey(externalKey)
    }

}