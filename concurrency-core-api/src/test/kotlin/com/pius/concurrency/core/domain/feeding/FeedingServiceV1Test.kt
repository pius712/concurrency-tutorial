package com.pius.concurrency.core.domain.feeding

import com.pius.concurrency.pet.v1.FoodEntity
import com.pius.concurrency.pet.v1.FoodRepository
import com.pius.concurrency.pet.v1.PetEntity
import com.pius.concurrency.pet.v1.PetRepository
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.test.context.TestConstructor
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors

@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class FeedingServiceV1Test(
    private val petRepository: PetRepository,
    private val foodRepository: FoodRepository,
    private val feedingService: FeedingServiceV1,
) {

    var petId: Long? = null
    var initialFood = 100L

    @BeforeEach
    fun setUp() {
        val petEntity = petRepository.save(PetEntity(power = 0))
        foodRepository.save(FoodEntity(petId = petEntity.id!!, count = initialFood))
        petId = petEntity.id!!
    }

    @AfterEach
    fun cleanUp() {
        petId?.let { petRepository.deleteById(it) }
    }


    @Test
    fun `비관적 락 적용`() {
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
        Assertions.assertThat(pet.power).isEqualTo(initialFood)
    }
}