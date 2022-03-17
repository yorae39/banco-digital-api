package com.example.bancodigital.controller

import com.example.bancodigital.log.logger
import com.example.bancodigital.model.JwtRequest
import com.example.bancodigital.model.JwtResponse
import com.example.bancodigital.util.JwtTokenUtil
import io.swagger.annotations.Api
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
@CrossOrigin
@Api(tags = ["Authentication JWT"])
class JwtAuthenticationController(
    private val authenticationManager: AuthenticationManager,
    private val jwtTokenUtil: JwtTokenUtil,
) {

    @PostMapping("/authenticate")
    fun createAuthenticationToken(@RequestBody authenticationRequest: JwtRequest): ResponseEntity<*> {
        val authentication = authenticationManager.authenticate(UsernamePasswordAuthenticationToken(
            authenticationRequest.username, authenticationRequest.password))
        val userDetails = authentication.principal as UserDetails
        logger().trace("Generate token for: ${authenticationRequest.username}")
        val token = jwtTokenUtil.generateToken(userDetails)
        return ResponseEntity.ok<Any>(JwtResponse(token))
    }

}