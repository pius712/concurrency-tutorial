package com.pius.concurrency.pet.v0

import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock

interface FoodRepositoryV0 : JpaRepository<FoodEntityV0, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    fun findByPetId(petId: Long): FoodEntityV0?
}