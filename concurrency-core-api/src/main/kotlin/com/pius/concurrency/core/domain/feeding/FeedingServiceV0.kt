package com.pius.concurrency.core.domain.feeding

import com.pius.concurrency.pet.v0.FoodRepositoryV0
import com.pius.concurrency.pet.v0.PetRepositoryV0
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class FeedingServiceV0(
    private val foodRepository: FoodRepositoryV0,
    private val petRepository: PetRepositoryV0,
) {

    // 락 없이 진행
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