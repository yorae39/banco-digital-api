package com.example.bancodigital.exception

class NationalRegistrationAlreadyExistsException(
    nationalRegistration: String
) : BusinessException(
    "There is already a registered holder with national registration [$nationalRegistration]"
)