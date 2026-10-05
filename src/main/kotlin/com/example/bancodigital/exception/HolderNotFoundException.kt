package com.example.bancodigital.exception

class HolderNotFoundException(
    message: String
) : RuntimeException(message) {

    constructor(id: Long) : this(
        "Holder with id $id not found"
    )
}