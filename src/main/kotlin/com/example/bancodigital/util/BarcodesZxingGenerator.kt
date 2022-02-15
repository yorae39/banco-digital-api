package com.example.bancodigital.util

import com.google.zxing.BarcodeFormat
import com.google.zxing.client.j2se.MatrixToImageWriter
import com.google.zxing.oned.Code128Writer
import org.springframework.stereotype.Component
import java.awt.image.BufferedImage


@Component
class BarcodesZxingGenerator {

    /*ACEITA QUALQUER STRING*/
    @Throws(Exception::class)
    fun generateCode128BarCodeImage(barcodeText: String): BufferedImage {
        val barcodeWriter = Code128Writer()
        val bitMatrix = barcodeWriter.encode(barcodeText, BarcodeFormat.CODE_128, 300, 150)
        return MatrixToImageWriter.toBufferedImage(bitMatrix)
    }

}