package com.pius.concurrency.coupon.v0

import com.pius.concurrency.BaseEntity
import jakarta.persistence.Entity

@Entity
data class CouponEntityV0(
    val userId: Long
) : BaseEntity() {
}