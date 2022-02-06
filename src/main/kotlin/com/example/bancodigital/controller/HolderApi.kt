package com.example.bancodigital.controller

import com.example.bancodigital.model.Holder
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.http.ResponseEntity
import java.util.Optional
import javax.servlet.http.HttpServletResponse

@Api
interface HolderApi {

    @ApiOperation(value = "Get list of holders in the System ", response = Iterable::class)
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "Success"),
        ApiResponse(code = 401, message = "Not authorized!"),
        ApiResponse(code = 403, message = "Forbidden!"),
        ApiResponse(code = 404, message = "Not found!")
    ])
    fun findAll(): List<Holder>

    @ApiOperation(value = "Create holder", response = String::class)
    fun createHolder(holder: Holder, httpServletResponse: HttpServletResponse): ResponseEntity<Any>

    @ApiOperation(value = "Get holder by id", response = Holder::class)
    fun findById(id: Long): ResponseEntity<Optional<Holder>>

    @ApiOperation(value = "Update holder by holder id", response = Holder::class)
    fun update(id: Long, holder: Holder): ResponseEntity<Holder>

    @ApiOperation(value = "Update active property status from by holder id", response = Holder::class)
    fun updateActiveProperty(id: Long, active: Boolean): ResponseEntity<String>

    @ApiOperation(value = "Delete holder by holder id", response = String::class)
    fun delete(id: Long): ResponseEntity<String>

}