package com.example.bancodigital.admin

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
interface AccountInternalApi {

    @ApiOperation(value = "Change number account from by account id", response = Account::class)
    fun changeNumber(id: Long): ResponseEntity<String>
}