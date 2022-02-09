package com.example.bancodigital.model.response

import com.example.bancodigital.model.Account
import java.time.LocalDate
import java.util.*

class AccountResponse(
    val id: Long?,
    val externalKey: UUID? = null,
    val holder: UUID?,
    val balance: Long,
    val number: String,
    val active: Boolean,
    val dateCreation: LocalDate,
) {

    override fun toString(): String {
        return "AccountResponse(id=$id, externalKey=$externalKey, holder=$holder, balance=$balance, number=$number, active=$active, dateCreation=$dateCreation)"
    }
    companion object {

        fun from(account: Account): AccountResponse =
            AccountResponse(
                id = account.id,
                externalKey = account.externalKey,
                holder = account.holder.externalKey,
                balance = account.balance,
                number = account.accountNumber.toString(),
                active = account.active,
                dateCreation = account.dateCreation
            )
    }
}