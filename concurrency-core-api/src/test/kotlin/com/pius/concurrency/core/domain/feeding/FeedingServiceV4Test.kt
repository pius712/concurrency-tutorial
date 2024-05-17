package com.pius.concurrency.core.domain.feeding

import com.pius.concurrency.pet.v4.FoodEntityV4
import com.pius.concurrency.pet.v4.FoodRepositoryV4
import com.pius.concurrency.pet.v4.PetEntityV4
import com.pius.concurrency.pet.v4.PetRepositoryV4
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
class FeedingServiceV4Test(
    private val petRepositoryV4: PetRepositoryV4,
    private val foodRepositoryV4: FoodRepositoryV4,
    private val feedingServiceV4: FeedingServiceV4
) {

    var petId: Long? = null
    var initialFood = 100L

    @BeforeEach
    fun setUp() {
        val petEntityV1 = petRepositoryV4.save(PetEntityV4(power = 0))
        foodRepositoryV4.save(FoodEntityV4(petId = petEntityV1.id!!, count = initialFood))
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
                    feedingServiceV4.feed(petId!!)
                } finally {
                    latch.countDown()
                }
            }
        }
        latch.await()
        val pet = petRepositoryV4.findByIdOrNull(petId!!) ?: throw RuntimeException("Pet not found")
        Assertions.assertThat(pet.power).isEqualTo(initialFood)
    }
}