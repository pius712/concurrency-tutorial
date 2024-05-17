package com.pius.concurrency.core.domain.feeding

import com.pius.concurrency.pet.v5.FoodRepositoryV5
import com.pius.concurrency.pet.v5.PetRepositoryV5
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class FeedingExecutorV5(
    private val foodRepositoryV5: FoodRepositoryV5,
    private val petRepositoryV5: PetRepositoryV5,
) {

    @Transactional
    fun feed(petId: Long) {
        val foodEntity = foodRepositoryV5.findByPetId(petId) ?: throw RuntimeException("Food not found")
        if (foodEntity.count <= 0) return;

        val petEntity = petRepositoryV5.findByIdOrNull(petId) ?: throw RuntimeException("Pet not found")
        foodEntity.count -= 1
        petEntity.power += 1;
    }
}