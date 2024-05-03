package com.pius.concurrency.core.domain.feeding

import com.pius.concurrency.pet.v2.FoodRepositoryV2
import com.pius.concurrency.pet.v2.PetRepositoryV2
import jakarta.transaction.Transactional
import jakarta.transaction.Transactional.TxType
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class FeedingExecutor(
    private val foodRepository: FoodRepositoryV2,
    private val petRepository: PetRepositoryV2,
) {

    @Transactional(value = TxType.REQUIRES_NEW)
    fun execute(petId: Long) {
        val foodEntity = foodRepository.findByPetId(petId) ?: throw RuntimeException("Food not found")
        if (foodEntity.count <= 0) return;

        val petEntity = petRepository.findByIdOrNull(petId) ?: throw RuntimeException("Pet not found")
        foodEntity.count -= 1
        petEntity.power += 1;
        return
    }
}