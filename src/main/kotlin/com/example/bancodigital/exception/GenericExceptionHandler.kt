package com.example.bancodigital.exception

import org.json.JSONObject
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import java.time.LocalDateTime


@ControllerAdvice
class GenericExceptionHandler {

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(Exception::class)
    fun methodArgumentNotValidException(ex: Exception?): ResponseEntity<*>? {
        val response = JSONObject()
        response.put("status", "Failure")
        response.put("message", "Something went Wrong, You probably did somthing wrong dummy")
        response.put("timestamp", LocalDateTime.now().toString())
        return ResponseEntity.badRequest().body(response.toString())
    }
}