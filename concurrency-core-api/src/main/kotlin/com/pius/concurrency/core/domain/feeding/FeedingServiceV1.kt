package com.pius.concurrency.core.domain.feeding

import com.pius.concurrency.pet.v1.FoodRepositoryV1
import com.pius.concurrency.pet.v1.PetRepositoryV1
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class FeedingServiceV1(
    private val foodRepositoryV1: FoodRepositoryV1,
    private val petRepositoryV1: PetRepositoryV1,
) {

    // 비관적 락
    @Transactional
    fun feed(
        petId: Long,
    ) {
        val foodEntity = foodRepositoryV1.findByPetId(petId) ?: throw RuntimeException("Food not found")
        if (foodEntity.count <= 0) return;

        val petEntity = petRepositoryV1.findByIdOrNull(petId) ?: throw RuntimeException("Pet not found")
        foodEntity.count -= 1
        petEntity.power += 1;
    }

}