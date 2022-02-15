package com.example.bancodigital.service

import com.example.bancodigital.model.BarcodeRegister
import com.example.bancodigital.repository.BarcodeRepository
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class BarcodeService(
    val barcodeRepository: BarcodeRepository
) {

    fun register(barcodeRegister: BarcodeRegister): BarcodeRegister {
        return barcodeRepository.save(barcodeRegister)
    }

    fun findByBarcode(externalKey: UUID): BarcodeRegister? {
        return barcodeRepository.findByExternalKey(externalKey)
    }

    fun findBarcodeByAccount(accountExternalKey: UUID) : List<BarcodeRegister>? {
        return barcodeRepository.findByAccountExternalKey(accountExternalKey)
    }

}