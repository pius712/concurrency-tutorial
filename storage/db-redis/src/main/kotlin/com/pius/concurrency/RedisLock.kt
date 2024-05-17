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
            Duration.ofSeconds(5)
        )!!
    }

    fun unlock(key: String, value: String) {
        val saved = redisTemplate.opsForValue().get(key)
        if (saved == value) {
            redisTemplate.delete(key)
        }
    }
}