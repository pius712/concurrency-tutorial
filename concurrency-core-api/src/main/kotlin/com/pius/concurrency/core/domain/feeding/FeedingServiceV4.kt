package com.pius.concurrency.core.domain.feeding

import com.pius.concurrency.RedisLock
import com.pius.concurrency.pet.v4.FoodRepositoryV4
import com.pius.concurrency.pet.v4.PetRepositoryV4
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.*

@Service
class FeedingServiceV4(
    private val foodRepositoryV4: FoodRepositoryV4,
    private val petRepositoryV4: PetRepositoryV4,
    private val redisLock: RedisLock,
) {

    // redis 락
    @Transactional
    fun feed(
        petId: Long,
    ) {

        val uuid = UUID.randomUUID().toString()
        try {
            while (!redisLock.lock(petId.toString(), uuid)) {
                Thread.sleep(500)
            }
            val foodEntity = foodRepositoryV4.findByPetId(petId) ?: throw RuntimeException("Food not found")
            if (foodEntity.count <= 0) return;

            val petEntity = petRepositoryV4.findByIdOrNull(petId) ?: throw RuntimeException("Pet not found")
            foodEntity.count -= 1
            petEntity.power += 1;
        } finally {
            redisLock.unlock(petId.toString(), uuid)
        }
    }
}