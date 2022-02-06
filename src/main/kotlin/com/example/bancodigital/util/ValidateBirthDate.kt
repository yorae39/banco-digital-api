package com.example.bancodigital.util

import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.Period

@Component
class ValidateBirthDate {

    fun calculateAge(birthDate: LocalDate, currentDate: LocalDate): Int {
        return Period.between(birthDate,currentDate).years
    }

}