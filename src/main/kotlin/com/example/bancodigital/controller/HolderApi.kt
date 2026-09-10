package com.example.bancodigital.controller

import com.example.bancodigital.dto.HolderDTO
import com.example.bancodigital.model.Holder
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import java.util.Optional
import jakarta.servlet.http.HttpServletResponse

@Tag(name = "Holder API", description = "Operações relacionadas a titulares de conta")
interface HolderApi {

    @Operation(summary = "Get list of holders in the System")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Success"),
        ApiResponse(responseCode = "401", description = "Not authorized!", content = [Content()]),
        ApiResponse(responseCode = "403", description = "Forbidden!", content = [Content()]),
        ApiResponse(responseCode = "404", description = "Not found!", content = [Content()])
    ])
    fun findAll(): List<Holder>

    @Operation(
        summary = "Create holder",
        responses = [ApiResponse(responseCode = "200", description = "Success", content = [Content(schema = Schema(implementation = String::class))])]
    )
    fun createHolder(holder: Holder, httpServletResponse: HttpServletResponse): ResponseEntity<Any>

    @Operation(
        summary = "Get holder by id",
        responses = [ApiResponse(responseCode = "200", description = "Success", content = [Content(schema = Schema(implementation = Holder::class))])]
    )
    fun findById(id: Long): ResponseEntity<Optional<Holder>>

    @Operation(
        summary = "Get holder by externalKey",
        responses = [ApiResponse(responseCode = "200", description = "Success", content = [Content(schema = Schema(implementation = Holder::class))])]
    )
    fun findByExternalKey(externalKey: String): ResponseEntity<Holder>

    @Operation(
        summary = "Update holder by holder id",
        responses = [ApiResponse(responseCode = "200", description = "Success", content = [Content(schema = Schema(implementation = Holder::class))])]
    )
    fun update(id: Long, holderDTO: HolderDTO): ResponseEntity<Any>

    @Operation(
        summary = "Update active property status from by holder id",
        responses = [ApiResponse(responseCode = "200", description = "Success", content = [Content(schema = Schema(implementation = Holder::class))])]
    )
    fun updateActiveProperty(id: Long, active: Boolean): ResponseEntity<String>
}