package com.example.bancodigital.repository

import com.example.bancodigital.dto.BankStatementDTO
import com.example.bancodigital.model.BankStatement
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.UUID

interface BankStatementRepository : JpaRepository<BankStatement, Long> {
    @Query("SELECT new com.example.bancodigital.dto.BankStatementDTO(t.description, t.value, bs.initialDate, " +
            " bs.finalDate, bs.accountExternalKey, bs.transactionType) " +
            " from Transaction t, BankStatementTransaction bst, BankStatement bs where bs.externalKey = ?1 " +
            " and bst.bankStatementId = bs.id and bst.transactionId = t.id")
    fun findSavedStatement(externalKey: UUID): List<BankStatementDTO>
}