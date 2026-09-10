package com.example.bancodigital.admin

import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/internal/accounts")
@Tag(name = "Account Internal")
class AccountInternalController : AccountInternalApi {

    @RequestMapping(
        value = ["/{id}"],
        method = [RequestMethod.POST],
        produces = [MediaType.ALL_VALUE]
    )
    override fun changeNumber(@PathVariable id: Long): ResponseEntity<String> {
        return ResponseEntity.ok("Test for id : $id")
    }
}