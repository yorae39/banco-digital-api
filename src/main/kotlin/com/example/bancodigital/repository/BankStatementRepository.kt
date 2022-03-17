package com.example.bancodigital.repository

import com.example.bancodigital.dto.BankStatementDTO
import com.example.bancodigital.model.BankStatement
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.UUID

interface BankStatementRepository : JpaRepository<BankStatement, Long> {
    @Query("SELECT new com.example.bancodigital.dto.BankStatementDTO(t.description, t.value, bs.initialDate, " +
            " bs.finalDate, bs.accountExternalKey, bs.transactionType) " +
            " FROM Transaction t, BankStatementTransaction bst, BankStatement bs WHERE bs.externalKey = ?1 " +
            " AND bst.bankStatementId = bs.id AND bst.transactionId = t.id")
    fun findSavedStatement(externalKey: UUID): List<BankStatementDTO>
}