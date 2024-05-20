package com.pius.concurrency.coupon.v2

import com.pius.concurrency.BaseEntity
import jakarta.persistence.Entity

@Entity
data class CouponEntityV2(
    val userId: Long
) : BaseEntity() {
}