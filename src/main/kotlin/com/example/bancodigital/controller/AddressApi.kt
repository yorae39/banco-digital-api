package com.example.bancodigital.controller

import com.example.bancodigital.dto.AddressDTO
import com.example.bancodigital.dto.HolderDTO
import com.example.bancodigital.model.Account
import com.example.bancodigital.model.Address
import com.example.bancodigital.model.Holder
import com.example.bancodigital.model.response.AccountResponse
import com.example.bancodigital.model.response.AddressResponse
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.http.ResponseEntity
import java.util.*
import javax.servlet.http.HttpServletResponse

@Api
interface AddressApi {

    @ApiOperation(value = "Get list of address in the System ", response = Iterable::class)
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "Success"),
        ApiResponse(code = 401, message = "Not authorized!"),
        ApiResponse(code = 403, message = "Forbidden!"),
        ApiResponse(code = 404, message = "Not found!")
    ])
    fun findAll(): List<Address>

    @ApiOperation(value = "Create account", response = String::class)
    fun createAddress(holderExternalKey: UUID, addressDTO: AddressDTO, httpServletResponse: HttpServletResponse): ResponseEntity<Any>

    @ApiOperation(value = "Get address by id", response = Address::class)
    fun findById(id: Long): ResponseEntity<Optional<Address>>

    @ApiOperation(value = "Update address by address id", response = Address::class)
    fun update(id: Long, addressDTO: AddressDTO): ResponseEntity<Any>
}