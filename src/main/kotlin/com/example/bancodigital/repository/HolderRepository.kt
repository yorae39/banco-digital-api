package com.example.bancodigital.repository

import com.example.bancodigital.model.Holder
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface HolderRepository : JpaRepository<Holder, Long> {
    fun findByNationalRegistration(nationalRegistration: String): Holder?
    fun findByExternalKey(externalKey: UUID): Holder?
}