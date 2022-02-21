package com.example.bancodigital.service

import com.example.bancodigital.model.Account
import com.example.bancodigital.repository.AccountRepository
import com.example.bancodigital.repository.HolderRepository
import com.example.bancodigital.util.ValidateAccountType
import org.springframework.stereotype.Service
import java.util.*

@Service
class AccountService(
    val accountRepository: AccountRepository,
    val holderRepository: HolderRepository,
    val validateAccountType: ValidateAccountType,
) {

    fun findAll(): List<Account> {
        return accountRepository.findAll()
    }

    fun createAccount(account: Account): Account {
        val holder = account.holder.id.let { holderRepository.findById(it) }
        val savedHolder = holderRepository.findByExternalKey(holder.get().externalKey)
        account.holder = savedHolder!!
        account.accountType = validateAccountType.returnMonthOfBirthDate(account.holder.birthDate)
        checkAlreadyExistsAccountNumber(account)
        return accountRepository.save(account)
    }

    fun findById(id: Long): Optional<Account> {
        return accountRepository.findById(id)
    }

    fun findByExternalKey(externalKey: String): Account? {
        return accountRepository.findByExternalKey(UUID.fromString(externalKey))
    }

    fun updateAccount(id: Long, account: Account): Account {
        val savedAccount = accountRepository.findById(id)
        val updateAccountHolder = Account.updateAccount(id, account, savedAccount)
        updateAccountHolder.accountType = validateAccountType.returnMonthOfBirthDate(account.holder.birthDate)
        return accountRepository.save(updateAccountHolder)
    }

    fun updateActiveProperty(id: Long, active: Boolean): String {
        val savedAccount = accountRepository.findById(id)
        return if (savedAccount.get().balance > 0) {
            "Existing balance needs to be reset to zero before deactivating the account"
        }else {
            val updateAccountProperty = Account.updateActiveProperty(id, savedAccount, active)
            accountRepository.save(updateAccountProperty)
            "Account id : $id updated property active for $active"
        }
    }

    fun validations(externalKey: UUID): String {
        val savedHolder = holderRepository.findByExternalKey(externalKey)
        if (savedHolder != null) {
            val listAccounts = accountRepository.findByHolder(savedHolder).filter { it.active }.size
            return if (listAccounts == 3) {
                "The number of accounts for this holder has reached the limit"
            } else if (!savedHolder.active) {
                "Holder status is invalid for account creation"
            } else {
                ""
            }
        }
        return "Holder not found in base"
    }

    fun checkAlreadyExistsAccountNumber(account: Account) {
        val accountList = accountRepository.findAccountNumbers()
        if (accountList.contains(account.accountNumber)) account.accountNumber = Account.randomAccountNumber()
    }
}