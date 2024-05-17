package com.pius.concurrency.core.domain.feeding

import com.pius.concurrency.pet.v0.FoodEntityV0
import com.pius.concurrency.pet.v0.FoodRepositoryV0
import com.pius.concurrency.pet.v0.PetEntityV0
import com.pius.concurrency.pet.v0.PetRepositoryV0
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
class FeedingServiceV0Test(
    private val petRepository: PetRepositoryV0,
    private val foodRepository: FoodRepositoryV0,
    private val feedingService: FeedingServiceV0,
) {

    var petId: Long? = null
    var initialFood = 100L

    @BeforeEach
    fun setUp() {
        val petEntityV1 = petRepository.save(PetEntityV0(power = 0))
        foodRepository.save(FoodEntityV0(petId = petEntityV1.id!!, count = initialFood))
        petId = petEntityV1.id!!
    }

    @AfterEach
    fun cleanUp() {
        petId?.let { petRepository.deleteById(it) }
    }


    @Test
    fun `락 없는 버전`() {
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
        val food = foodRepository.findByIdOrNull(petId!!) ?: throw RuntimeException("Pet not found")
        Assertions.assertThat(food.count).isEqualTo(0)
        Assertions.assertThat(pet.power).isEqualTo(initialFood)
    }
}