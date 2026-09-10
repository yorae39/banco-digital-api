package com.example.bancodigital.converter

import java.time.LocalDate
import java.time.ZoneId
import java.util.*
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter


/*@Converter(autoApply = true)
class LocalDateConverter : AttributeConverter<LocalDate, Date> {

    override fun convertToDatabaseColumn(localDate: LocalDate): Date {
        return Date.from(localDate.atStartOfDay()
            .atZone(ZoneId.systemDefault())
            .toInstant());
    }

    override fun convertToEntityAttribute(date: Date): LocalDate {
        return date.toInstant()
            .atZone(ZoneId.systemDefault())
            .toLocalDate();
    }

}*/
