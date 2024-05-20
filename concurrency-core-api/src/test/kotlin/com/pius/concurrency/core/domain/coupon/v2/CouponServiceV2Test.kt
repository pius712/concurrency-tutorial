package com.pius.concurrency.core.domain.coupon.v2

import com.pius.concurrency.coupon.CouponClient
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestConstructor
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors

@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class CouponServiceV2Test(
    private val couponServiceV2: CouponServiceV2,
    private val couponClient: CouponClient
) {

    @Test
    fun `write behind`() {
        val requestCount = 1000
        val countDownLatch = CountDownLatch(1000)
        val executorService = Executors.newFixedThreadPool(100)
        for (i in 1L..requestCount) {
            executorService.submit {
                runCatching { couponServiceV2.issue(i) }
                countDownLatch.countDown()
            }
        }

        countDownLatch.await()
        val count = couponClient.count()
        Assertions.assertThat(count).isEqualTo(100)
    }
}