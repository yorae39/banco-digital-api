package com.example.bancodigital.facade

import com.example.bancodigital.model.Account
import com.example.bancodigital.model.Holder
import com.example.bancodigital.service.AccountService
import org.springframework.stereotype.Component
import java.util.*

@Component
class AccountFacade(
    private val accountService: AccountService,
) {

    fun findAll(): List<Account> {
        return accountService.findAll()
    }

    fun validations(externalKey: UUID): String {
        return accountService.validations(externalKey)
    }

    fun createAccount(account: Account): Account {
        return accountService.createAccount(account)
    }

    fun findById(id: Long): Optional<Account> {
        return accountService.findById(id)
    }

    fun findByExternalKey(externalKey: String): Account? {
        return accountService.findByExternalKey(externalKey)
    }

    fun updateAccount(id: Long, account: Account): Account {
        return accountService.updateAccount(id, account)
    }

    fun updateActiveProperty(id: Long, active: Boolean): String {
        return accountService.updateActiveProperty(id, active)
    }

    fun savedHolderByExternalKey(externalKey: String): Holder? {
        return accountService.returnSaveHolder(UUID.fromString(externalKey))
    }
}