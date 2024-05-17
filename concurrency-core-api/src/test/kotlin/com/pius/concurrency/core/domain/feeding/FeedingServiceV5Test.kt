package com.pius.concurrency.core.domain.feeding

import com.pius.concurrency.pet.v5.FoodEntityV5
import com.pius.concurrency.pet.v5.FoodRepositoryV5
import com.pius.concurrency.pet.v5.PetEntityV5
import com.pius.concurrency.pet.v5.PetRepositoryV5
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.test.context.TestConstructor
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors

@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class FeedingServiceV5Test(
    private val feedingServiceV5: FeedingServiceV5,
    private val foodRepositoryV5: FoodRepositoryV5,
    private val petRepositoryV5: PetRepositoryV5
) {

    var petId: Long? = null
    var initialFood = 100L

    @BeforeEach
    fun setUp() {
        val petEntityV1 = petRepositoryV5.save(PetEntityV5(power = 0))
        foodRepositoryV5.save(FoodEntityV5(petId = petEntityV1.id!!, count = initialFood))
        petId = petEntityV1.id!!
    }

    @Test
    fun feedTest() {
        val clickCount = 1000
        val executorService = Executors.newFixedThreadPool(32)
        val latch = CountDownLatch(clickCount)

        for (i in 1..clickCount) {
            executorService.execute {
                try {
                    feedingServiceV5.feed(petId!!)
                } finally {
                    latch.countDown()
                }
            }
        }
        latch.await()
        executorService.shutdown()
        val pet = petRepositoryV5.findByIdOrNull(petId!!) ?: throw RuntimeException("Pet not found")
        val food = foodRepositoryV5.findByPetId(petId!!) ?: throw RuntimeException("food not found")
        Assertions.assertThat(pet.power).isEqualTo(initialFood)
        Assertions.assertThat(food.count).isEqualTo(0)
    }
}