package com.example.bancodigital.util

import com.example.bancodigital.model.AccountType
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class ValidateAccountType {
    fun returnMonthOfBirthDate(birthDate: LocalDate): AccountType {
        val list = listOf(1, 3, 4, 11) //Janeiro, Março, Abril ou Novembro
        return if (list.contains(birthDate.month.value)) AccountType.VIP else AccountType.NORMAL
    }
}