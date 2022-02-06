package com.example.bancodigital.repository

import com.example.bancodigital.model.Holder
import org.springframework.data.jpa.repository.JpaRepository

interface HolderRepository : JpaRepository<Holder, Long> {
}