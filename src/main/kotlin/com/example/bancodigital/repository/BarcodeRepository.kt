package com.example.bancodigital.repository

import com.example.bancodigital.model.BarcodeRegister
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface BarcodeRepository : JpaRepository<BarcodeRegister, Long> {
    fun findByExternalKey(externalKey: UUID): BarcodeRegister?
    fun findByAccountExternalKey(accountExternalKey: UUID): List<BarcodeRegister>?
}