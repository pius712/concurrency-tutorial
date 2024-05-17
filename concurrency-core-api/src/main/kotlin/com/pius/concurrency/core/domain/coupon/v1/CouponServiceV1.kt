package com.pius.concurrency.core.domain.coupon.v1

import com.pius.concurrency.coupon.v1.CouponEntityV1
import com.pius.concurrency.coupon.v1.CouponRepositoryV1
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Service

@Service
class CouponServiceV1(
    private val stringRedisTemplate: StringRedisTemplate,
    private val couponRepositoryV1: CouponRepositoryV1
) {

    fun issue(userId: Long) {
        val count = stringRedisTemplate.opsForValue().increment("coupon")!!
        if (count > 100) {
            return
        }
        couponRepositoryV1.save(CouponEntityV1(userId))
    }
}