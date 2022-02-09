package com.example.bancodigital.dto

import com.example.bancodigital.model.Account
import com.example.bancodigital.model.Holder

class AccountDTO(
    val holder: Holder
) {

    companion object {
        fun from(holder: Holder) = Account(
            holder = holder
        )
    }
}