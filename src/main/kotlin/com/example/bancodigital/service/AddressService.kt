package com.example.bancodigital.service

import com.example.bancodigital.controller.AddressApi
import com.example.bancodigital.dto.AddressDTO
import com.example.bancodigital.model.Address
import com.example.bancodigital.model.response.AddressResponse
import com.example.bancodigital.repository.AddressRepository
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.util.*
import javax.servlet.http.HttpServletResponse

@Service
class AddressService(
    val addressRepository: AddressRepository
) {
    fun findAll(): List<Address> {
        return addressRepository.findAll()
    }

    fun createAddress(address: Address): Address {
        return addressRepository.save(address)
    }

    fun findById(id: Long): Optional<Address>{
        return addressRepository.findById(id)
    }

    fun update(id: Long, addressDTO: AddressDTO): Address {
        val savedAddress = addressRepository.findById(id)
        val updateAddress = Address.updateAddress(id, addressDTO, savedAddress)
        return addressRepository.save(updateAddress)
    }


}