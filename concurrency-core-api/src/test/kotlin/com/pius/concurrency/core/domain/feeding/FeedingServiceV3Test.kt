package com.pius.concurrency.core.domain.feeding

import com.pius.concurrency.pet.v3.FoodEntityV3
import com.pius.concurrency.pet.v3.FoodRepositoryV3
import com.pius.concurrency.pet.v3.PetEntityV3
import com.pius.concurrency.pet.v3.PetRepositoryV3
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
class FeedingServiceV3Test(
    private val petRepository: PetRepositoryV3,
    private val foodRepository: FoodRepositoryV3,
    private val feedingService: FeedingServiceV3,
) {

    var petId: Long? = null
    var initialFood = 100L
    val logger = LoggerFactory.getLogger(FeedingServiceV2Test::class.java)

    @BeforeEach
    fun setUp() {
        val petEntity = petRepository.save(PetEntityV3(power = 0))
        foodRepository.save(FoodEntityV3(petId = petEntity.id!!, count = initialFood))
        petId = petEntity.id!!
    }

    @Test
    fun `database index table 사용`() {
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