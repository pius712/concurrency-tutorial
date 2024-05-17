package com.pius.concurrency.core.domain.feeding

import jakarta.transaction.Transactional
import org.redisson.api.RedissonClient
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit

@Service
class FeedingServiceV5(
    private val feedingExecutorV5: FeedingExecutorV5,
    private val redissonClient: RedissonClient,
) {
    val logger = LoggerFactory.getLogger(javaClass)


    @Transactional
    fun feed(
        petId: Long,
    ) {
        val lock = redissonClient.getLock(petId.toString())
        try {
            val isLocked = lock.tryLock(10, 1, TimeUnit.SECONDS)
            if (isLocked) {
                feedingExecutorV5.feed(petId)
                lock.unlock()
            }
        } finally {
            lock.unlock()
        }

    }
}