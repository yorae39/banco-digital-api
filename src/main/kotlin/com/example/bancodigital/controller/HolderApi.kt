package com.example.bancodigital.controller

import com.example.bancodigital.dto.CreateHolderDTO
import com.example.bancodigital.dto.UpdateHolderDTO
import com.example.bancodigital.model.response.HolderResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.ResponseEntity

@Tag(
    name = "Holder API",
    description = "Operações relacionadas a titulares de conta"
)
interface HolderApi {

    @Operation(summary = "Get list of holders in the System")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Success"),
            ApiResponse(responseCode = "401", description = "Not authorized!", content = [Content()]),
            ApiResponse(responseCode = "403", description = "Forbidden!", content = [Content()])
        ]
    )
    fun findAll(): List<HolderResponse>

    @Operation(
        summary = "Create holder",
        responses = [
            ApiResponse(
                responseCode = "201",
                description = "Holder created successfully",
                content = [
                    Content(
                        schema = Schema(implementation = HolderResponse::class)
                    )
                ]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Invalid holder data",
                content = [Content()]
            )
        ]
    )
    fun createHolder(
        createHolderDTO: CreateHolderDTO
    ): ResponseEntity<Any>

    @Operation(
        summary = "Get holder by id",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Success",
                content = [
                    Content(
                        schema = Schema(implementation = HolderResponse::class)
                    )
                ]
            ),
            ApiResponse(
                responseCode = "404",
                description = "Holder not found",
                content = [Content()]
            )
        ]
    )
    fun findById(id: Long): ResponseEntity<HolderResponse>

    @Operation(
        summary = "Get holder by externalKey",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Success",
                content = [
                    Content(
                        schema = Schema(implementation = HolderResponse::class)
                    )
                ]
            ),
            ApiResponse(
                responseCode = "404",
                description = "Holder not found",
                content = [Content()]
            )
        ]
    )
    fun findByExternalKey(externalKey: String): ResponseEntity<HolderResponse>

    @Operation(
        summary = "Update holder by holder id",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Holder updated successfully",
                content = [
                    Content(
                        schema = Schema(implementation = HolderResponse::class)
                    )
                ]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Invalid holder data",
                content = [Content()]
            ),
            ApiResponse(
                responseCode = "404",
                description = "Holder not found",
                content = [Content()]
            )
        ]
    )
    fun update(
        id: Long,
        updateHolderDTO: UpdateHolderDTO
    ): ResponseEntity<Any>

    @Operation(
        summary = "Update active property status by holder id",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Active property updated successfully",
                content = [
                    Content(
                        schema = Schema(implementation = String::class)
                    )
                ]
            ),
            ApiResponse(
                responseCode = "404",
                description = "Holder not found",
                content = [Content()]
            )
        ]
    )
    fun updateActiveProperty(
        id: Long,
        active: Boolean
    ): ResponseEntity<String>
}