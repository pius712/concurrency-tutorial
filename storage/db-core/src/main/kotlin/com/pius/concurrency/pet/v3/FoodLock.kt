package com.pius.concurrency.pet.v3

import jakarta.transaction.Transactional
import org.springframework.stereotype.Component

@Component
class FoodLock(
    private val foodLockRepository: FoodLockRepository
) {

    fun lock(lockKey: String) {
        try {
            foodLockRepository.save(FoodLockEntity(lockKey))
        } catch (e: Exception) {
            throw RuntimeException("Failed to lock food", e)
        }
    }

    @Transactional
    fun release(lockKey: String) {
        try {
            foodLockRepository.deleteByLockKey(lockKey)
        } catch (e: Exception) {
            throw RuntimeException("Failed to release food lockKey: $lockKey", e)
        }
    }
}