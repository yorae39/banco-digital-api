package com.example.bancodigital.exception

import org.json.JSONObject
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import java.time.LocalDateTime


@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
class ValidationFailureExceptionHandler {

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun methodArgumentNotValidException(ex: MethodArgumentNotValidException): ResponseEntity<*> {
        val result = ex.bindingResult
        val fieldErrors = result.fieldErrors
        val errorMessage = fieldErrors[0].defaultMessage
        val response = JSONObject()
        response.put("status", "Failure")
        response.put("message", errorMessage)
        response.put("timestamp", LocalDateTime.now().toString())
        return ResponseEntity.badRequest().body(response.toString())
    }
}