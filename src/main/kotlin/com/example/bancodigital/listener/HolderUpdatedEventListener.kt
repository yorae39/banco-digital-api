package com.example.bancodigital.listener

import com.example.bancodigital.event.HolderUpdatedEvent
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class HolderUpdatedEventListener {

    private val logger =
        LoggerFactory.getLogger(HolderUpdatedEventListener::class.java)

    @TransactionalEventListener(
        phase = TransactionPhase.AFTER_COMMIT
    )
    fun handle(event: HolderUpdatedEvent) {
        logger.info(
            "Holder update committed successfully: id={}, externalKey={}",
            event.holderId,
            event.externalKey
        )
    }
}