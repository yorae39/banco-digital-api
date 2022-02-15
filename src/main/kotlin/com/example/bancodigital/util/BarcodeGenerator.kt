package com.example.bancodigital.util

import net.sourceforge.barbecue.BarcodeFactory
import net.sourceforge.barbecue.BarcodeImageHandler
import org.springframework.stereotype.Component
import java.awt.Font
import java.awt.image.BufferedImage


@Component
class BarcodeGenerator {

    /* CASO ALGUÉM QUEIRA USAR OUTRA TECNOLOGIA FICA AQUI ALGUNS EXEMPLOS*/

    @Throws(Exception::class)
    fun generateEAN13BarcodeImage(barcodeText: String): BufferedImage {
        val barcode = BarcodeFactory.createEAN13(barcodeText)
        barcode.font = Font(Font.SANS_SERIF, Font.PLAIN, 12)
        return BarcodeImageHandler.getImage(barcode)
    }

    @Throws(Exception::class)
    fun generateUPCBarcodeImage(barcodeText: String): BufferedImage {
        val barcode = BarcodeFactory.createUPCA(barcodeText)
        barcode.font = Font(Font.SANS_SERIF, Font.PLAIN, 12)
        return BarcodeImageHandler.getImage(barcode)
    }

    @Throws(Exception::class)
    fun generateEAN128BarCodeImage(barcodeText: String): BufferedImage {
        val barcode = BarcodeFactory.createEAN128(barcodeText)
        barcode.font = Font(Font.SANS_SERIF, Font.PLAIN, 12)
        return BarcodeImageHandler.getImage(barcode)
    }

    @Throws(Exception::class)
    fun generateCode128BarCodeImage(barcodeText: String): BufferedImage {
        val barcode = BarcodeFactory.createCode128(barcodeText)
        barcode.font = Font(Font.SANS_SERIF, Font.PLAIN, 12)
        return BarcodeImageHandler.getImage(barcode)
    }

    @Throws(Exception::class)
    fun generateUSPSBarcodeImage(barcodeText: String): BufferedImage {
        val barcode = BarcodeFactory.createUSPS(barcodeText)
        barcode.font = Font(Font.SANS_SERIF, Font.PLAIN, 12)
        return BarcodeImageHandler.getImage(barcode)
    }

    @Throws(Exception::class)
    fun generateSCC14ShippingCodeBarcodeImage(barcodeText: String): BufferedImage {
        val barcode = BarcodeFactory.createSCC14ShippingCode(barcodeText)
        barcode.font = Font(Font.SANS_SERIF, Font.PLAIN, 12)
        return BarcodeImageHandler.getImage(barcode)
    }

    @Throws(Exception::class)
    fun generateCode39BarcodeImage(barcodeText: String): BufferedImage {
        val barcode = BarcodeFactory.createCode39(barcodeText, true)
        barcode.font = Font(Font.SANS_SERIF, Font.PLAIN, 12)
        return BarcodeImageHandler.getImage(barcode)
    }

    @Throws(Exception::class)
    fun generateGlobalTradeItemNumberBarcodeImage(barcodeText: String): BufferedImage {
        val barcode = BarcodeFactory.createGlobalTradeItemNumber(barcodeText)
        barcode.font = Font(Font.SANS_SERIF, Font.PLAIN, 12)
        return BarcodeImageHandler.getImage(barcode)
    }

    @Throws(Exception::class)
    fun generatePDF417BarcodeImage(barcodeText: String): BufferedImage {
        val barcode = BarcodeFactory.createPDF417(barcodeText)
        barcode.font = Font(Font.SANS_SERIF, Font.PLAIN, 12)
        return BarcodeImageHandler.getImage(barcode)
    }

}