package com.example.bancodigital.event

import java.util.UUID

data class HolderCreatedEvent(
    val holderId: Long,
    val externalKey: UUID
)