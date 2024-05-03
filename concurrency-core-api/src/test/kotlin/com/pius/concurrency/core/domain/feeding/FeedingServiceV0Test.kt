package com.pius.concurrency.core.domain.feeding

import com.pius.concurrency.pet.v1.FoodEntityV1
import com.pius.concurrency.pet.v1.FoodRepositoryV1
import com.pius.concurrency.pet.v1.PetEntityV1
import com.pius.concurrency.pet.v1.PetRepositoryV1
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
    private val petRepository: PetRepositoryV1,
    private val foodRepositoryV1: FoodRepositoryV1,
    private val feedingService: FeedingServiceV0,
) {

    var petId: Long? = null
    var initialFood = 100L

    @BeforeEach
    fun setUp() {
        val petEntityV1 = petRepository.save(PetEntityV1(power = 0))
        foodRepositoryV1.save(FoodEntityV1(petId = petEntityV1.id!!, count = initialFood))
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
        Assertions.assertThat(pet.power).isEqualTo(initialFood)
    }
}