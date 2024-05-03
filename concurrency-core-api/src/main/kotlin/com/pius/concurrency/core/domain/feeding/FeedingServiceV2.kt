package com.pius.concurrency.core.domain.feeding

import org.slf4j.LoggerFactory
import org.springframework.orm.ObjectOptimisticLockingFailureException
import org.springframework.stereotype.Service

@Service
class FeedingServiceV2(
    private val feedingExecutor: FeedingExecutor
) {

    val logger = LoggerFactory.getLogger(javaClass)

    // 낙관적 락
    fun feed(
        petId: Long,
    ) {
        while (true) {
            try {
                feedingExecutor.execute(petId)
                return;
            } catch (e: ObjectOptimisticLockingFailureException) {
                continue
            } catch (e: Exception) {
                break;
            }
        }
    }
}