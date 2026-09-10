package com.example.bancodigital.controller

import com.example.bancodigital.dto.AddressDTO
import com.example.bancodigital.model.Address
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import java.util.*
import jakarta.servlet.http.HttpServletResponse

@Tag(name = "Address API", description = "Operações relacionadas a endereços")
interface AddressApi {

    @Operation(summary = "Get list of address in the System")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Success"),
        ApiResponse(responseCode = "401", description = "Not authorized!", content = [Content()]),
        ApiResponse(responseCode = "403", description = "Forbidden!", content = [Content()]),
        ApiResponse(responseCode = "404", description = "Not found!", content = [Content()])
    ])
    fun findAll(): List<Address>

    @Operation(
        summary = "Create account",
        responses = [ApiResponse(responseCode = "200", description = "Success", content = [Content(schema = Schema(implementation = String::class))])]
    )
    fun createAddress(holderExternalKey: UUID, addressDTO: AddressDTO, httpServletResponse: HttpServletResponse): ResponseEntity<Any>

    @Operation(
        summary = "Get address by id",
        responses = [ApiResponse(responseCode = "200", description = "Success", content = [Content(schema = Schema(implementation = Address::class))])]
    )
    fun findById(id: Long): ResponseEntity<Optional<Address>>

    @Operation(
        summary = "Update address by address id",
        responses = [ApiResponse(responseCode = "200", description = "Success", content = [Content(schema = Schema(implementation = Address::class))])]
    )
    fun update(id: Long, addressDTO: AddressDTO): ResponseEntity<Any>
}