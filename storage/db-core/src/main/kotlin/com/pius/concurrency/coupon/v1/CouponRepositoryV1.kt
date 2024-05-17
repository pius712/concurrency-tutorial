package com.pius.concurrency.coupon.v1

import org.springframework.data.jpa.repository.JpaRepository

interface CouponRepositoryV1 : JpaRepository<CouponEntityV1, Long> {
}