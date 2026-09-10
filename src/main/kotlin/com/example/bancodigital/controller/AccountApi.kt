package com.example.bancodigital.controller

import com.example.bancodigital.model.Account
import com.example.bancodigital.model.response.AccountResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import java.util.*
import jakarta.servlet.http.HttpServletResponse

@Tag(name = "Account API", description = "Operações relacionadas a contas")
interface AccountApi {

    @Operation(summary = "Get list of accounts in the System")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Success"),
        ApiResponse(responseCode = "401", description = "Not authorized!", content = [Content()]),
        ApiResponse(responseCode = "403", description = "Forbidden!", content = [Content()]),
        ApiResponse(responseCode = "404", description = "Not found!", content = [Content()])
    ])
    fun findAll(): List<Account>

    @Operation(
        summary = "Create account",
        responses = [ApiResponse(responseCode = "200", description = "Success", content = [Content(schema = Schema(implementation = String::class))])]
    )
    fun createAccount(holderExternalKey: UUID, httpServletResponse: HttpServletResponse): ResponseEntity<Any>

    @Operation(
        summary = "Get account by id",
        responses = [ApiResponse(responseCode = "200", description = "Success", content = [Content(schema = Schema(implementation = Account::class))])]
    )
    fun findById(id: Long): ResponseEntity<Optional<Account>>

    @Operation(
        summary = "Get account by externalKey",
        responses = [ApiResponse(responseCode = "200", description = "Success", content = [Content(schema = Schema(implementation = AccountResponse::class))])]
    )
    fun findByExternalKey(externalKey: String): ResponseEntity<AccountResponse>

    @Operation(
        summary = "Update account by account id",
        responses = [ApiResponse(responseCode = "200", description = "Success", content = [Content(schema = Schema(implementation = Account::class))])]
    )
    fun transferAccountOtherHolder(id: Long, holderExternalKey: UUID): ResponseEntity<Any>

    @Operation(
        summary = "Update active property status from by account id",
        responses = [ApiResponse(responseCode = "200", description = "Success", content = [Content(schema = Schema(implementation = Account::class))])]
    )
    fun updateActiveProperty(id: Long, active: Boolean): ResponseEntity<String>
}