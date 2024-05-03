package com.pius.concurrency.core.domain.feeding

import com.pius.concurrency.pet.v3.FoodLock
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class FeedingServiceV3_2(
    private val feedingExecutor: FeedingExecutorV3_2,
    private val foodLock: FoodLock,
) {

    val logger = LoggerFactory.getLogger(javaClass)

    // 아, 테스트 스레드가 안끝나는 이유가
    // 아래 release 에서 에러가 나서, latch.countDown 이 호출이 안되서 그럼
    fun feed(
        petId: Long,
    ) {
        while (true) {
            try {
                foodLock.lock(petId.toString())
                feedingExecutor.feed(petId)
                return
            } catch (e: Exception) {
                Thread.sleep(500)
                throw RuntimeException("Failed to lock food", e)
            } finally {
                foodLock.release(petId.toString())

            }
        }
    }
}