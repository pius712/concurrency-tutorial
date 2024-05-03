package com.pius.concurrency.core.domain.feeding

import com.pius.concurrency.pet.v1.FoodRepository
import com.pius.concurrency.pet.v1.PetRepository
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class FeedingServiceV1(
    private val foodRepository: FoodRepository,
    private val petRepository: PetRepository,
) {

    // 비관적 락
    @Transactional
    fun feed(
        petId: Long,
    ) {
        val foodEntity = foodRepository.findByPetId(petId) ?: throw RuntimeException("Food not found")
        if (foodEntity.count <= 0) return;

        val petEntity = petRepository.findByIdOrNull(petId) ?: throw RuntimeException("Pet not found")
        foodEntity.count -= 1
        petEntity.power += 1;
    }

}