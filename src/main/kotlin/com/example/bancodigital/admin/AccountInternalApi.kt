package com.example.bancodigital.admin

import com.example.bancodigital.model.Account
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity

@Tag(name = "Account Internal API", description = "Operações internas de contas")
interface AccountInternalApi {

    @Operation(
        summary = "Change number account from by account id",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Conta alterada com sucesso",
                content = [Content(schema = Schema(implementation = Account::class))]
            )
        ]
    )
    fun changeNumber(id: Long): ResponseEntity<String>
}