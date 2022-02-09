package com.example.bancodigital.test

import com.example.bancodigital.util.ValidateBirthDate
import com.example.bancodigital.util.ValidateNationalRegistration
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDate

@SpringBootTest
class ValidateTest {

    @Autowired
    private lateinit var validateNationalRegistration: ValidateNationalRegistration
    @Autowired
    private lateinit var  validateBirthDate: ValidateBirthDate

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
    fun `validate valid birthdate`(nationalRegistration: String): Unit =
        validateBD(nationalRegistration).let {
            assertThat(it).isTrue
        }

    @ParameterizedTest(name = "Should fail for birth date \"{0}\"")
    @ValueSource(strings = ["2022-01-01", "2005-01-01", "2008-01-01"])
    fun `validate invalid birthdate`(nationalRegistration: String): Unit =
        validateBD(nationalRegistration).let {
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