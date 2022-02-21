package com.example.bancodigital.repository

import com.example.bancodigital.model.Account
import com.example.bancodigital.model.Holder
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface AccountRepository : JpaRepository<Account, Long> {
    fun findByExternalKey(externalKey: UUID): Account?
    fun findByHolder(holder: Holder): List<Account>
    @Query("SELECT a.accountNumber FROM Account a")
    fun findAccountNumbers(): List<Long>
}