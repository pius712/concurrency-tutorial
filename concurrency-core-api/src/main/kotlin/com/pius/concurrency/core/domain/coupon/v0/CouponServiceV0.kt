package com.pius.concurrency.core.domain.coupon.v0

import com.pius.concurrency.coupon.v0.CouponEntityV0
import com.pius.concurrency.coupon.v0.CouponRepositoryV0
import org.springframework.stereotype.Service

@Service
class CouponServiceV0(
    private val couponRepositoryV0: CouponRepositoryV0
) {

    fun issue(userId: Long) {
        val count = couponRepositoryV0.count()
        if (count > 100) {
            return
        }
        couponRepositoryV0.save(CouponEntityV0(userId))
    }
}