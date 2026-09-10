package com.example.bancodigital.admin

import com.example.bancodigital.dto.DebitByQrcodeDTO
import com.example.bancodigital.dto.QrCodeGenerationDTO
import com.example.bancodigital.service.QrCodeService
import com.google.zxing.NotFoundException
import com.google.zxing.WriterException
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import jakarta.servlet.http.HttpServletResponse
import jakarta.validation.Valid

@RestController
@RequestMapping("/internal/qrcode")
@Tag(name = "QR-code Generator and Reader")
class QrCodeController(
    val qrCodeService: QrCodeService
) {

    @PostMapping(value = ["/generate"])
    @ResponseStatus(value = HttpStatus.OK)
    @Operation(summary = "Returns a .png QR code with provided information decoded inside")
    @Throws(IOException::class, WriterException::class)
    fun qrCodeGenerationHandler(
        @RequestBody(required = true) qrCodeGenerationRequestDto: @Valid QrCodeGenerationDTO,
        httpServletResponse: HttpServletResponse,
    ) {
        qrCodeService.generate(qrCodeGenerationRequestDto, httpServletResponse)
    }

    @PostMapping(value = ["/generate/transaction"])
    @ResponseStatus(value = HttpStatus.OK)
    @Operation(summary = "Returns a .png QR code with provided information decoded inside for transaction")
    @Throws(IOException::class, WriterException::class)
    fun qrCodeGenerationHandlerForTransaction(
        @RequestBody(required = true) debitByQrcodeDTO: @Valid DebitByQrcodeDTO,
        httpServletResponse: HttpServletResponse,
    ) {
        qrCodeService.generateForTransactions(debitByQrcodeDTO, httpServletResponse)
    }

    @PutMapping(value = ["/read"], consumes = [MULTIPART_FORM_DATA_VALUE])
    @ResponseStatus(value = HttpStatus.OK)
    @Operation(summary = "returns decoded information inside provided QR code")
    @Throws(
        IOException::class,
        NotFoundException::class
    )
    fun read(
        @Parameter(description = "Qr-code for read", required = true)
        @RequestPart("file") file: MultipartFile,
    ): ResponseEntity<*> {
        return qrCodeService.read(file)
    }
}