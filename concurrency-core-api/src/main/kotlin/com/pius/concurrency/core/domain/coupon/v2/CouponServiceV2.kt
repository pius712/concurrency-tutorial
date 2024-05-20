package com.pius.concurrency.core.domain.coupon.v2

import com.pius.concurrency.coupon.CouponClient
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Service

@Service
class CouponServiceV2(
    private val stringRedisTemplate: StringRedisTemplate,
    private val couponClient: CouponClient,
) {

    // write behind
    // redis 에 저장 후 db 에 저장
    fun issue(userId: Long) {
        val count = stringRedisTemplate.opsForValue().increment("couponV2:count")!!
        if (count > 100) {
            return
        }
        couponClient.save(userId)
    }
}