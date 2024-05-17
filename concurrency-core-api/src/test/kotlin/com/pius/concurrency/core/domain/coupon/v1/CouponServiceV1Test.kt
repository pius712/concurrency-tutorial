package com.pius.concurrency.core.domain.coupon.v1

import com.pius.concurrency.coupon.v1.CouponRepositoryV1
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestConstructor
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors

@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class CouponServiceV1Test(
    private val couponRepositoryV1: CouponRepositoryV1,
    private val couponServiceV1: CouponServiceV1
) {

    @Test
    fun `redis incr`() {
        val requestCount = 1000
        val countDownLatch = CountDownLatch(1000)
        val executorService = Executors.newFixedThreadPool(100)
        for (i in 1L..requestCount) {
            executorService.submit {
                runCatching { couponServiceV1.issue(i) }
                countDownLatch.countDown()
            }
        }

        countDownLatch.await()
        val count = couponRepositoryV1.count()
        Assertions.assertThat(count).isEqualTo(100)
    }
}