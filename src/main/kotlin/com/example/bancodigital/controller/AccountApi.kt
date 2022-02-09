package com.example.bancodigital.controller

import com.example.bancodigital.model.Account
import com.example.bancodigital.model.response.AccountResponse
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.http.ResponseEntity
import java.util.*
import javax.servlet.http.HttpServletResponse

@Api
interface AccountApi {

    @ApiOperation(value = "Get list of accounts in the System ", response = Iterable::class)
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "Success"),
        ApiResponse(code = 401, message = "Not authorized!"),
        ApiResponse(code = 403, message = "Forbidden!"),
        ApiResponse(code = 404, message = "Not found!")
    ])
    fun findAll(): List<Account>

    @ApiOperation(value = "Create account", response = String::class)
    fun createAccount(holderExternalKey: UUID, httpServletResponse: HttpServletResponse): ResponseEntity<Any>

    @ApiOperation(value = "Get account by id", response = Account::class)
    fun findById(id: Long): ResponseEntity<Optional<Account>>

    @ApiOperation(value = "Get account by externalKey", response = Account::class)
    fun findByExternalKey(externalKey: String): ResponseEntity<AccountResponse>

    @ApiOperation(value = "Update account by account id", response = Account::class)
    fun transferAccountOtherHolder(id: Long, holderExternalKey: UUID): ResponseEntity<Any>

    @ApiOperation(value = "Update active property status from by account id", response = Account::class)
    fun updateActiveProperty(id: Long, active: Boolean): ResponseEntity<String>
}