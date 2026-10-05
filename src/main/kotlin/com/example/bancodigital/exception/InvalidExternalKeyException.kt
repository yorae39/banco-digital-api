package com.example.bancodigital.exception

class InvalidExternalKeyException(
    externalKey: String
) : RuntimeException(
    "Invalid externalKey format: $externalKey"
)