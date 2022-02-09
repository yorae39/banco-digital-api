package com.example.bancodigital.model

import com.example.bancodigital.converter.LocalDateConverter
import com.example.bancodigital.dto.AccountDTO
import com.example.bancodigital.dto.HolderDTO
import com.example.bancodigital.util.Constants.Companion.MAX_ACCOUNT_NUMBER
import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.hibernate.annotations.Type
import java.time.LocalDate
import java.util.*
import javax.persistence.Column
import javax.persistence.Convert
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.validation.constraints.NotNull
import kotlin.random.Random

@Entity
data class Account(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Type(type="uuid-char")
    val externalKey: UUID = UUID.randomUUID(),
    var balance: Long = 0,
    val active: Boolean = true,
    var accountNumber: Long = randomAccountNumber(),
    @Convert(converter = LocalDateConverter::class)
    val dateCreation: LocalDate = LocalDate.now(),
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var accountType: AccountType = AccountType.NORMAL,
    @ManyToOne
    @JoinColumn
    var holder: Holder,
    @OneToMany(mappedBy = "account")
    @Column(nullable = true)
    @JsonIgnore
    val transactions: List<Transaction> = listOf(),
){

    fun changeNumber() {
        accountNumber = randomAccountNumber()
    }

    companion object {
        fun randomAccountNumber() =
            Random.nextLong(MAX_ACCOUNT_NUMBER)

        fun updateAccount(id: Long, account: Account, savedAccount: Optional<Account>) = Account(
            id = id,
            externalKey = savedAccount.get().externalKey,
            balance = savedAccount.get().balance,
            active = savedAccount.get().active,
            accountNumber = savedAccount.get().accountNumber,
            dateCreation = savedAccount.get().dateCreation,
            accountType = savedAccount.get().accountType,
            holder = account.holder,
            transactions = savedAccount.get().transactions
        )

        fun updateActiveProperty(id: Long, savedAccount: Optional<Account>, active: Boolean) = Account(
            id = id,
            externalKey = savedAccount.get().externalKey,
            balance = savedAccount.get().balance,
            active = active,
            accountNumber = savedAccount.get().accountNumber,
            dateCreation = savedAccount.get().dateCreation,
            accountType = savedAccount.get().accountType,
            holder = savedAccount.get().holder,
            transactions = savedAccount.get().transactions
        )
    }
}