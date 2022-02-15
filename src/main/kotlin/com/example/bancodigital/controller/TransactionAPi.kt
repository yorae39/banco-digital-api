package com.example.bancodigital.controller

import com.example.bancodigital.dto.CreditDTO
import com.example.bancodigital.dto.DebitDTO
import com.example.bancodigital.model.Transaction
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.http.ResponseEntity
import java.util.*

@Api
interface TransactionAPi {

    @ApiOperation(value = "Get list of transactions of a account in the System ", response = Iterable::class)
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "Success"),
        ApiResponse(code = 401, message = "Not authorized!"),
        ApiResponse(code = 403, message = "Forbidden!"),
        ApiResponse(code = 404, message = "Not found!")
    ])
    fun findTransactionsByAccount(id: Long): List<Transaction>

    @ApiOperation(value = "Credit by account", response = String::class)
    fun creditByAccount(creditDTO: CreditDTO, externalKey: UUID): ResponseEntity<String>

    @ApiOperation(value = "Debit by account", response = String::class)
    fun debitByAccount(debitDTO: DebitDTO, externalKey: UUID): ResponseEntity<Any>
}