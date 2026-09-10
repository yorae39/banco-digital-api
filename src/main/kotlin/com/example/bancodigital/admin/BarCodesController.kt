package com.example.bancodigital.admin

import com.example.bancodigital.dto.BarcodeInfoDTO
import com.example.bancodigital.util.BarcodeReader
import com.example.bancodigital.util.BarcodesZxingGenerator
import com.google.zxing.NotFoundException
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType.ALL_VALUE
import org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.awt.image.BufferedImage
import java.io.File
import java.io.IOException
import javax.imageio.ImageIO

@RestController
@RequestMapping("/internal/barcode")
@Tag(name = "Barcode Generator and Reader")
class BarCodesController(
    val barcodesZxingGenerator: BarcodesZxingGenerator,
    val barcodeReader: BarcodeReader
) {

    @PostMapping(value = ["/generate/{barcodeText}"], produces = [ALL_VALUE])
    @ResponseStatus(value = HttpStatus.OK)
    @Operation(summary = "Returns a .png Barcode code with provided information decoded inside")
    fun generate(
        @PathVariable("barcodeText") barcodeText: String,
    ): ResponseEntity<String> {
        val resp = ok(barcodesZxingGenerator.generateCode128BarCodeImage(barcodeText))
        return ResponseEntity.ok(resp.body.toString())
    }

    @PutMapping(value = ["/read"], consumes = [MULTIPART_FORM_DATA_VALUE])
    @ResponseStatus(value = HttpStatus.OK)
    @Operation(summary = "returns decoded information inside provided barcode")
    @Throws(
        IOException::class,
        NotFoundException::class
    )
    fun read(
        @Parameter(description = "Barcode for read", required = true)
        @RequestPart("file") file: MultipartFile,
    ): ResponseEntity<BarcodeInfoDTO> {
        return ResponseEntity.ok(barcodeReader.decodeImage(file))
    }

    private fun ok(bufferedImage: BufferedImage): ResponseEntity<String> {
        save(bufferedImage)
        return ResponseEntity.ok("Barcode saved for consult")
    }

    /*O IDEAL SERIA SALVAR NO BANCO...*/
    private fun save(bufferedImage: BufferedImage) {
        val barcode = File("/home/luiz/Testes/Estudo/codigo-barras.jpg")
        ImageIO.write(bufferedImage, "jpg", barcode)
    }
}