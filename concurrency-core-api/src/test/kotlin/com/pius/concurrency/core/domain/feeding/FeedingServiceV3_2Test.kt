package com.pius.concurrency.core.domain.feeding

import com.pius.concurrency.pet.v3.*
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
class FeedingServiceV3_2Test(
    private val petRepository: PetRepositoryV3,
    private val foodRepository: FoodRepositoryV3,
    private val feedingService: FeedingServiceV3_2,
    private val foodLock: FoodLock,
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

    // 이 테스트는 request count 에 따라 성공 실패 여부가 갈림
    // why? release 자체가 버그가 있어서,
    // release 에 실패하는 경우가 생김.
    // 근데 카운트가 워낙 많다보니, 많은 요청중 일부는 lock 획득 성공 함.
    // 그래서 성공하는 경우도 있음.
    @Test
    fun `database index table 사용`() {
        val threadname = Thread.currentThread().name
        val clickRequestCount = 130

        val latch = CountDownLatch(clickRequestCount)
        val executorService = Executors.newFixedThreadPool(32)

        for (i in 1..clickRequestCount) {
            executorService.execute {
                try {
                    feedingService.feed(petId!!)
                } finally {
                    latch.countDown()
                }
            }
        }

        // 테스트 스레드 대기
        latch.await()
        val pet = petRepository.findByIdOrNull(petId!!) ?: throw RuntimeException("Pet not found")
        Assertions.assertThat(pet.power).isEqualTo(initialFood)
    }

    @Test
    fun `아무데이터 없이 제거`() {
        foodLock.release(petId.toString())

    }
}