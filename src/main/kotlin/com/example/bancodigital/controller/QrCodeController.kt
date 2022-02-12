package com.example.bancodigital.controller

import com.example.bancodigital.dto.QrCodeGenerationDTO
import com.example.bancodigital.service.QrCodeService
import com.google.zxing.NotFoundException
import com.google.zxing.WriterException
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import lombok.AllArgsConstructor
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import javax.servlet.http.HttpServletResponse
import javax.validation.Valid


@RestController
@AllArgsConstructor
@Api(tags = ["QR-code Generator and Reader"])
class QrCodeController(
    val qrCodeService: QrCodeService
) {

    @PostMapping(value = ["/generate"])
    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(value = "Returns a .png QR code with provided information decoded inside")
    @Throws(IOException::class, WriterException::class)
    fun qrCodeGenerationHandler(
        @RequestBody(required = true) qrCodeGenerationRequestDto: @Valid QrCodeGenerationDTO,
        httpServletResponse: HttpServletResponse,
    ) {
        qrCodeService.generate(qrCodeGenerationRequestDto, httpServletResponse)
    }

    @PutMapping(value = ["/read"], consumes = ["multipart/form-data"])
    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(value = "returns decoded information inside provided QR code")
    @Throws(
        IOException::class,
        NotFoundException::class)
    fun read(@RequestParam(value = "Qr-code for read",  required = true) file: MultipartFile,
    ): ResponseEntity<*> {
        return qrCodeService.read(file)
    }
}