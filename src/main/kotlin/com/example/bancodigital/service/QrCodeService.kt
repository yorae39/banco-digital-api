package com.example.bancodigital.service

import com.example.bancodigital.dto.DebitByQrcodeDTO
import com.example.bancodigital.dto.QrCodeGenerationDTO
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.zxing.BarcodeFormat
import com.google.zxing.BinaryBitmap
import com.google.zxing.LuminanceSource
import com.google.zxing.MultiFormatReader
import com.google.zxing.NotFoundException
import com.google.zxing.WriterException
import com.google.zxing.client.j2se.BufferedImageLuminanceSource
import com.google.zxing.client.j2se.MatrixToImageWriter
import com.google.zxing.common.HybridBinarizer
import com.google.zxing.qrcode.QRCodeWriter
import com.google.zxing.Result
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.BufferedOutputStream
import java.io.IOException
import javax.imageio.ImageIO
import javax.servlet.http.HttpServletResponse


@Service
class QrCodeService {

    @Throws(IOException::class, NotFoundException::class)
    fun read(file: MultipartFile): ResponseEntity<*> {
        val bufferedImage = ImageIO.read(file.inputStream)
        val luminanceSource: LuminanceSource = BufferedImageLuminanceSource(bufferedImage)
        val binaryBitmap = BinaryBitmap(HybridBinarizer(luminanceSource))
        val result: Result = MultiFormatReader().decode(binaryBitmap)
        return ResponseEntity.ok(ObjectMapper().readValue(result.text, QrCodeGenerationDTO::class.java))
    }

    @Throws(IOException::class, WriterException::class)
    fun generate(
        qrCodeGenerationDTO: QrCodeGenerationDTO,
        httpServletResponse: HttpServletResponse,
    ) {
        httpServletResponse.setHeader(HttpHeaders.CONTENT_DISPOSITION,
            "attachment;filename=" + qrCodeGenerationDTO.title.trim().replace(" ", "_").toString() + ".png")
        val outputStream = BufferedOutputStream(httpServletResponse.outputStream)
        val writer = QRCodeWriter()
        val bitMatrix = writer.encode(ObjectMapper().writeValueAsString(qrCodeGenerationDTO),
            BarcodeFormat.QR_CODE, 350, 350)
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream)
        outputStream.flush()
    }

    @Throws(IOException::class, WriterException::class)
    fun generateForTransactions(
        dto: DebitByQrcodeDTO,
        response: HttpServletResponse,
    ) {
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
            "attachment;filename=" + dto.description.trim().replace(" ", "_").toString() + ".png")
        val outputStream = BufferedOutputStream(response.outputStream)
        val writer = QRCodeWriter()
        val bitMatrix = writer.encode(ObjectMapper().writeValueAsString(dto),
            BarcodeFormat.QR_CODE, 350, 350)
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream)
        outputStream.flush()
    }

    @Throws(IOException::class, NotFoundException::class)
    fun readForQrcodeTransaction(file: MultipartFile): DebitByQrcodeDTO {
        val bufferedImage = ImageIO.read(file.inputStream)
        val luminanceSource: LuminanceSource = BufferedImageLuminanceSource(bufferedImage)
        val binaryBitmap = BinaryBitmap(HybridBinarizer(luminanceSource))
        val result: Result = MultiFormatReader().decode(binaryBitmap)
        return ObjectMapper().readValue(result.text, DebitByQrcodeDTO::class.java)
    }


}