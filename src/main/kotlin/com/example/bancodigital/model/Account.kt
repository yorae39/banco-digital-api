package com.example.bancodigital.model

import com.example.bancodigital.util.Constants.Companion.MAX_ACCOUNT_NUMBER
import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDate
import java.util.*
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes
import kotlin.random.Random

@Entity
data class Account(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(name = "external_key", columnDefinition = "VARCHAR(36)")
    val externalKey: UUID = UUID.randomUUID(),
    var balance: Long = 0,
    val active: Boolean = true,
    var accountNumber: Long = randomAccountNumber(),
    @JsonFormat(pattern = "dd/MM/yyyy")
    val dateCreation: LocalDate = LocalDate.now(),
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var accountType: AccountType = AccountType.NORMAL,
    @ManyToOne
    @JoinColumn
    var holder: Holder
){

    fun changeNumber() {
        accountNumber = randomAccountNumber()
    }

    companion object {

        fun randomAccountNumber() = Random.nextLong(MAX_ACCOUNT_NUMBER)

        fun updateAccount(id: Long, account: Account, savedAccount: Optional<Account>) = Account(
            id = id,
            externalKey = savedAccount.get().externalKey,
            balance = savedAccount.get().balance,
            active = savedAccount.get().active,
            accountNumber = savedAccount.get().accountNumber,
            dateCreation = savedAccount.get().dateCreation,
            accountType = savedAccount.get().accountType,
            holder = account.holder
        )

        fun updateActiveProperty(id: Long, savedAccount: Optional<Account>, active: Boolean) = Account(
            id = id,
            externalKey = savedAccount.get().externalKey,
            balance = savedAccount.get().balance,
            active = active,
            accountNumber = savedAccount.get().accountNumber,
            dateCreation = savedAccount.get().dateCreation,
            accountType = savedAccount.get().accountType,
            holder = savedAccount.get().holder
        )

        fun operationOfCredit(balance: Long, savedAccount: Account) =
            Account(
                id = savedAccount.id,
                externalKey = savedAccount.externalKey,
                balance = balance + savedAccount.balance,
                active = savedAccount.active,
                accountNumber = savedAccount.accountNumber,
                dateCreation = savedAccount.dateCreation,
                accountType = savedAccount.accountType,
                holder = savedAccount.holder
            )

        fun operationOfDebit(savedAccount: Account) =
            Account(
                id = savedAccount.id,
                externalKey = savedAccount.externalKey,
                balance = savedAccount.balance,
                active = savedAccount.active,
                accountNumber = savedAccount.accountNumber,
                dateCreation = savedAccount.dateCreation,
                accountType = savedAccount.accountType,
                holder = savedAccount.holder
            )
    }
}