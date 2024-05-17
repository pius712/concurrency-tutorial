package com.pius.concurrency.coupon.v1

import com.pius.concurrency.BaseEntity
import jakarta.persistence.Entity

@Entity
data class CouponEntityV1(
    val userId: Long
) : BaseEntity() {
}