package com.example.bancodigital.util

import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class ParseStringToLocalDate {
    fun parse(inputDate: String): LocalDate {
        return LocalDate.parse(inputDate)
    }
}