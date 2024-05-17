package com.pius.concurrency

import org.redisson.api.RedissonClient
import org.springframework.stereotype.Component

@Component
class RedissonLock(
    private val redissonClient: RedissonClient
) {
    fun lock(key: String) {
        val lock = redissonClient.getLock(key)
        lock.lock()
    }

    fun unlock(key: String) {
        val lock = redissonClient.getLock(key)
        lock.unlock()
    }
}
