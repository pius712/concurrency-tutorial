package com.pius.concurrency.core.domain.coupon.v0

import com.pius.concurrency.coupon.v0.CouponRepositoryV0
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestConstructor
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors

@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class CouponServiceV0Test(
    private val couponRepositoryV0: CouponRepositoryV0,
    private val couponServiceV0: CouponServiceV0
) {


    // 테스트 실패
    @Test
    fun `락 없이 진행`() {
        val requestCount = 1000
        val countDownLatch = CountDownLatch(1000)
        val executorService = Executors.newFixedThreadPool(100)
        for (i in 1L..requestCount) {
            executorService.submit {
                runCatching { couponServiceV0.issue(i) }
                countDownLatch.countDown()
            }
        }

        countDownLatch.await()
        val count = couponRepositoryV0.count()
        Assertions.assertThat(count).isEqualTo(100)
    }
}