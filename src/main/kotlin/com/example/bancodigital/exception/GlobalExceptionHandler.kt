package com.example.bancodigital.exception

import com.example.bancodigital.model.response.ErrorResponse
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    private val logger = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)

    @ExceptionHandler(HolderNotFoundException::class)
    fun handleHolderNotFound(
        ex: HolderNotFoundException
    ): ResponseEntity<ErrorResponse> {

        val error = ErrorResponse(
            status = HttpStatus.NOT_FOUND.value(),
            error = HttpStatus.NOT_FOUND.reasonPhrase,
            message = ex.message ?: "Holder not found"
        )

        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(error)
    }

    @ExceptionHandler(InvalidExternalKeyException::class)
    fun handleInvalidExternalKey(
        ex: InvalidExternalKeyException
    ): ResponseEntity<ErrorResponse> {

        val error = ErrorResponse(
            status = HttpStatus.BAD_REQUEST.value(),
            error = HttpStatus.BAD_REQUEST.reasonPhrase,
            message = ex.message ?: "Invalid externalKey"
        )

        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(error)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidation(
        ex: MethodArgumentNotValidException
    ): ResponseEntity<ErrorResponse> {

        val message = ex.bindingResult.fieldErrors
            .firstOrNull()
            ?.defaultMessage
            ?: "Invalid request"

        val error = ErrorResponse(
            status = HttpStatus.BAD_REQUEST.value(),
            error = HttpStatus.BAD_REQUEST.reasonPhrase,
            message = message
        )

        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(error)
    }

    @ExceptionHandler(Exception::class)
    fun handleGenericException(
        ex: Exception
    ): ResponseEntity<ErrorResponse> {

        logger.error("Unexpected error", ex)

        val error = ErrorResponse(
            status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
            error = HttpStatus.INTERNAL_SERVER_ERROR.reasonPhrase,
            message = "An unexpected error occurred"
        )

        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(error)
    }
}