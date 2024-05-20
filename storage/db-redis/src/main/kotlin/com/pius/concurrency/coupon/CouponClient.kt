package com.pius.concurrency.coupon

import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Component
import java.time.Instant

@Component
class CouponClient(
    private val stringRedisTemplate: StringRedisTemplate
) {

    fun save(userId: Long) {
        stringRedisTemplate.opsForZSet()
            .add("couponV2:user", userId.toString(), Instant.now().toEpochMilli().toDouble())
    }

    fun count(): Long {
        return stringRedisTemplate.opsForZSet().size("couponV2:user") ?: 0
    }
}