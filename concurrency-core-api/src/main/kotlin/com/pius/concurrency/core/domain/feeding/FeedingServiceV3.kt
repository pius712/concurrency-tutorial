package com.pius.concurrency.core.domain.feeding

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class FeedingServiceV3(
    private val feedingExecutor: FeedingExecutorV3,
) {
    val logger = LoggerFactory.getLogger(javaClass)

    // table unique index
    fun feed(
        petId: Long,
    ) {
        while (true) {
            try {
                feedingExecutor.feed(petId)
                return
            } catch (e: Exception) {
                logger.error("Failed to feed petId: $petId", e)
                Thread.sleep(500)
            }
        }
    }
}