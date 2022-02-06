package com.example.bancodigital.repository

import com.example.bancodigital.model.Account
import org.springframework.data.jpa.repository.JpaRepository

interface AccountRepository : JpaRepository<Account, Long> {
}