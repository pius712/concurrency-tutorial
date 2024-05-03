package com.pius.concurrency.core.domain.feeding

import com.pius.concurrency.pet.v3.FoodLock
import com.pius.concurrency.pet.v3.FoodRepositoryV3
import com.pius.concurrency.pet.v3.PetRepositoryV3
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class FeedingExecutorV3(
    private val foodRepositoryV3: FoodRepositoryV3,
    private val petRepositoryV3: PetRepositoryV3,
    private val foodLock: FoodLock,
) {

    @Transactional
    fun feed(petId: Long) {
        try {
            foodLock.lock(petId.toString())
        } catch (e: Exception) {
            throw RuntimeException("Failed to lock food", e)
        }

        try {
            val foodEntity = foodRepositoryV3.findByPetId(petId) ?: throw RuntimeException("Food not found")
            if (foodEntity.count <= 0) return;

            val petEntity = petRepositoryV3.findByIdOrNull(petId) ?: throw RuntimeException("Pet not found")
            foodEntity.count -= 1
            petEntity.power += 1;
        } finally {
            foodLock.release(petId.toString())
        }

    }
}