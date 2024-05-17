package com.pius.concurrency.coupon.v0

import org.springframework.data.jpa.repository.JpaRepository

interface CouponRepositoryV0 : JpaRepository<CouponEntityV0, Long>