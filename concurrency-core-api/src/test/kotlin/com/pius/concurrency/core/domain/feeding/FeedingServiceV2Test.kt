package com.pius.concurrency.core.domain.feeding

import com.pius.concurrency.pet.v2.FoodEntityV2
import com.pius.concurrency.pet.v2.FoodRepositoryV2
import com.pius.concurrency.pet.v2.PetEntityV2
import com.pius.concurrency.pet.v2.PetRepositoryV2
import jakarta.transaction.Transactional
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.test.context.TestConstructor
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors

@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
// 여기 부분이 중요합니다. @Transactional 어노테이션을 붙이면 테스트가 실패합니다.
// 여기 낙관적 락 내부에서 에러 발생시, 이 트랜잭션
@Transactional

class FeedingServiceV2Test(
    private val petRepository: PetRepositoryV2,
    private val foodRepository: FoodRepositoryV2,
    private val feedingService: FeedingServiceV2,
) {

    var petId: Long? = null
    var initialFood = 100L
    val logger = LoggerFactory.getLogger(FeedingServiceV2Test::class.java)

    @BeforeEach
    fun setUp() {
        val petEntity = petRepository.save(PetEntityV2(power = 0))
        foodRepository.save(FoodEntityV2(petId = petEntity.id!!, count = initialFood))
        petId = petEntity.id!!
    }

    @Test
    fun `낙관적 락 적용`() {
        logger.info("테스트 시작")
        val threadname = Thread.currentThread().name
        val clickRequestCount = 1000

        val latch = CountDownLatch(clickRequestCount)
        val executorService = Executors.newFixedThreadPool(32)

        for (i in 1..clickRequestCount) {
            executorService.execute {
                feedingService.feed(petId!!)
                latch.countDown()
            }
        }

        // 테스트 스레드 대기
        latch.await()
        val pet = petRepository.findByIdOrNull(petId!!) ?: throw RuntimeException("Pet not found")
        logger.info(
            "너의 thread 이름은 ${Thread.currentThread().name} +  :  + ${threadname} ${pet}"
        )
        Assertions.assertThat(pet.power).isEqualTo(initialFood)

    }
}