package com.example.bancodigital.listener

import com.example.bancodigital.event.HolderCreatedEvent
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class HolderCreatedEventListener {

    private val logger =
        LoggerFactory.getLogger(HolderCreatedEventListener::class.java)

    @TransactionalEventListener(
        phase = TransactionPhase.AFTER_COMMIT
    )
    fun handle(event: HolderCreatedEvent) {
        logger.info(
            "Holder creation committed successfully: id={}, externalKey={}",
            event.holderId,
            event.externalKey
        )
    }
}