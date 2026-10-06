package com.example.bancodigital.exception

class InvalidNationalRegistrationException(
    nationalRegistration: String
) : BusinessException(
    "Holder national registration [$nationalRegistration] is invalid"
)