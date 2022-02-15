package com.example.bancodigital.util

import com.example.bancodigital.dto.BarcodeInfoDTO
import com.example.bancodigital.dto.CreditByBarcode
import com.example.bancodigital.exception.BarcodeDecodingException
import com.google.zxing.BinaryBitmap
import com.google.zxing.MultiFormatReader
import com.google.zxing.Reader
import com.google.zxing.Result
import com.google.zxing.client.j2se.BufferedImageLuminanceSource
import com.google.zxing.common.HybridBinarizer
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import javax.imageio.ImageIO


@Component
class BarcodeReader {

    @Throws(BarcodeDecodingException::class)
    fun decodeImage(file: MultipartFile): BarcodeInfoDTO? {
        return try {
            var bitmap = BinaryBitmap(HybridBinarizer(
                BufferedImageLuminanceSource(ImageIO.read(file.inputStream))))
            if (bitmap.width < bitmap.height) {
                if (bitmap.isRotateSupported) {
                    bitmap = bitmap.rotateCounterClockwise()
                }
            }
            decode(bitmap)
        } catch (e: IOException) {
            throw BarcodeDecodingException(e.message.toString())
        }
    }

    @Throws(BarcodeDecodingException::class)
    private fun decode(bitmap: BinaryBitmap): BarcodeInfoDTO? {
        val reader: Reader = MultiFormatReader()
        return try {
            val result: Result = reader.decode(bitmap)
            BarcodeInfoDTO(result.text, result.barcodeFormat.toString())
        } catch (e: Exception) {
            throw BarcodeDecodingException(e.message.toString())
        }
    }

    @Throws(BarcodeDecodingException::class)
    fun decodeImageForCredit(file: MultipartFile): CreditByBarcode {
        return try {
            var bitmap = BinaryBitmap(HybridBinarizer(
                BufferedImageLuminanceSource(ImageIO.read(file.inputStream))))
            if (bitmap.width < bitmap.height) {
                if (bitmap.isRotateSupported) {
                    bitmap = bitmap.rotateCounterClockwise()
                }
            }
            decodeForCredit(bitmap)
        } catch (e: IOException) {
            throw BarcodeDecodingException(e.message.toString())
        }
    }

    @Throws(BarcodeDecodingException::class)
    private fun decodeForCredit(bitmap: BinaryBitmap): CreditByBarcode {
        val reader: Reader = MultiFormatReader()
        return try {
            val result: Result = reader.decode(bitmap)
            CreditByBarcode(result.text.toBigDecimal())
        } catch (e: Exception) {
            throw BarcodeDecodingException(e.message.toString())
        }
    }

}