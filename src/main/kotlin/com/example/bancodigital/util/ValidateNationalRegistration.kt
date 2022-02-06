package com.example.bancodigital.util

import org.springframework.stereotype.Component
import java.util.*

@Component
class ValidateNationalRegistration {

    fun isNationalRegistration(nationalRegistration: String): Boolean {
        // Considera-se erro CPF's formados por uma sequencia de numeros iguais
        val errorList = listOf("00000000000", "1111111111", "22222222222", "33333333333", "44444444444", "55555555555",
            "66666666666", "77777777777", "88888888888", "99999999999")
        if (errorList.contains(nationalRegistration) || nationalRegistration.length != 11)  return false

        val dig10: Char
        val dig11: Char
        var sm: Int
        var i: Int
        var r: Int
        var num: Int
        var peso: Int

        // "try" - protege o codigo para eventuais erros de conversao de tipo (int)
        return try {
            // Calculo do 1o. Digito Verificador
            sm = 0
            peso = 10
            i = 0
            while (i < 9) {
                // converte o i-esimo caractere do CPF em um numero:
                // por exemplo, transforma o caractere '0' no inteiro 0
                // (48 eh a posicao de '0' na tabela ASCII)
                num = (nationalRegistration[i].code - 48)
                sm += num * peso
                peso -= 1
                i++
            }
            r = 11 - sm % 11
            dig10 = if (r == 10 || r == 11) '0' else (r + 48).toChar() // converte no respectivo caractere numerico

            // Calculo do 2o. Digito Verificador
            sm = 0
            peso = 11
            i = 0
            while (i < 10) {
                num = (nationalRegistration[i].code - 48)
                sm += num * peso
                peso -= 1
                i++
            }
            r = 11 - sm % 11
            dig11 = if (r == 10 || r == 11) '0' else (r + 48).toChar()

            // Verifica se os digitos calculados conferem com os digitos informados.
            dig10 == nationalRegistration[9] && dig11 == nationalRegistration[10]
        } catch (e: InputMismatchException) {
            false
        }
    }

    fun printNationalRegistration(nationalRegistration: String): String {
        return nationalRegistration.substring(0, 3) + "." + nationalRegistration.substring(3, 6) + "." +
                nationalRegistration.substring(6, 9) + "-" + nationalRegistration.substring(9, 11)
    }
}