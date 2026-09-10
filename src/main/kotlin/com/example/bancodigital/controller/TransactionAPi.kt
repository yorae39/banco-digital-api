package com.example.bancodigital.controller

import com.example.bancodigital.dto.CreditDTO
import com.example.bancodigital.dto.DebitDTO
import com.example.bancodigital.model.Transactions
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import java.util.*

@Tag(name = "Transaction API", description = "Operações relacionadas a transações bancárias")
interface TransactionAPi {

    @Operation(summary = "Get list of transactions of a account in the System")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Success", content = [Content(schema = Schema(implementation = String::class))]),
        ApiResponse(responseCode = "401", description = "Not authorized!", content = [Content()]),
        ApiResponse(responseCode = "403", description = "Forbidden!", content = [Content()]),
        ApiResponse(responseCode = "404", description = "Not found!", content = [Content()])
    ])
    fun findTransactionsByAccount(id: Long): List<Transactions>

    @Operation(
        summary = "Credit by account",
        responses = [ApiResponse(responseCode = "200", description = "Success", content = [Content(schema = Schema(implementation = String::class))])]
    )
    fun creditByAccount(creditDTO: CreditDTO, externalKey: UUID): ResponseEntity<String>

    @Operation(
        summary = "Debit by account",
        responses = [ApiResponse(responseCode = "200", description = "Success", content = [Content(schema = Schema(implementation = String::class))])]
    )
    fun debitByAccount(debitDTO: DebitDTO, externalKey: UUID): ResponseEntity<Any>
}