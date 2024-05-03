package com.pius.concurrency.pet.v3

import org.springframework.data.jpa.repository.JpaRepository

interface FoodLockRepository : JpaRepository<FoodLockEntity, Long> {
    fun deleteByLockKey(lockKey: String): Unit
}