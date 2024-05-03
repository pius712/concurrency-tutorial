package com.pius.concurrency.pet.v2

import org.springframework.data.jpa.repository.JpaRepository

interface FoodRepositoryV2 : JpaRepository<FoodEntityV2, Long> {
    fun findByPetId(petId: Long): FoodEntityV2?
    fun deleteByPetId(it: Long): Unit
}