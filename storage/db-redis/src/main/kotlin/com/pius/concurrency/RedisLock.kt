package com.pius.concurrency

import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Component
import java.time.Duration

@Component
class RedisLock(
    private val redisTemplate: StringRedisTemplate
) {

    fun lock(key: String, value: String): Boolean {
        return redisTemplate.opsForValue().setIfAbsent(
            key, value,
            Duration.ofSeconds(10)
        )!!
    }

    fun unlock(key: String) {
        redisTemplate.delete(key)
    }
}