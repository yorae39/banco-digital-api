package com.example.bancodigital.event

import java.util.UUID

data class HolderUpdatedEvent(
    val holderId: Long,
    val externalKey: UUID
)