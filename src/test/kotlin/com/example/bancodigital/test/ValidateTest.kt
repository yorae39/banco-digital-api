package com.example.bancodigital.test

import com.example.bancodigital.util.ValidateBirthDate
import com.example.bancodigital.util.ValidateNationalRegistration
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import java.time.LocalDate

class ValidateTest {

    private val validateNationalRegistration = ValidateNationalRegistration()

    private val validateBirthDate = ValidateBirthDate()

    @ParameterizedTest(name = "Should success for national registration \"{0}\"")
    @ValueSource(strings = ["68947376060", "22258529018", "40281852030"])
    fun `validate valid national registration`(nationalRegistration: String): Unit =
        validateNR(nationalRegistration).let {
            assertThat(it).isTrue
    }

    @ParameterizedTest(name = "Should fail for national registration \"{0}\"")
    @ValueSource(strings = ["99999999999", "00000000000", "09249615034"])
    fun `validate invalid national registration`(nationalRegistration: String): Unit =
        validateNR(nationalRegistration).let {
            assertThat(it).isFalse
        }

    @ParameterizedTest(name = "Should success for birth date \"{0}\"")
    @ValueSource(strings = ["2004-01-01", "1974-07-13", "1977-03-17"])
    fun `validate valid birthdate`(birthdate: String): Unit =
        validateBD(birthdate).let {
            assertThat(it).isTrue
        }

    @ParameterizedTest(name = "Should fail for birth date \"{0}\"")
    @ValueSource(strings = ["2026-01-01", "2009-01-01", "2012-01-01"])
    fun `validate invalid birthdate`(birthdate: String): Unit =
        validateBD(birthdate).let {
            assertThat(it).isFalse
        }

    private fun validateNR(nationalRegistration: String): Boolean {
        return when {
            validateNationalRegistration.isNationalRegistration(nationalRegistration) -> true
            else -> false
        }
    }

    private fun validateBD(birthDate: String): Boolean {
        return when {
            validateBirthDate.calculateAge(LocalDate.parse(birthDate), LocalDate.now()) < 18 -> false
            else -> true
        }
    }
}