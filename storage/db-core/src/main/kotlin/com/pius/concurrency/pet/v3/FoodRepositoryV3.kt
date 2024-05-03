package com.pius.concurrency.pet.v3

import org.springframework.data.jpa.repository.JpaRepository

interface FoodRepositoryV3 : JpaRepository<FoodEntityV3, Long> {
    fun findByPetId(petId: Long): FoodEntityV3?
    fun deleteByPetId(it: Long): Unit
}