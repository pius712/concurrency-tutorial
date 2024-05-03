package com.pius.concurrency.pet.v1

import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock

interface FoodRepositoryV1 : JpaRepository<FoodEntityV1, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    fun findByPetId(petId: Long): FoodEntityV1?
}