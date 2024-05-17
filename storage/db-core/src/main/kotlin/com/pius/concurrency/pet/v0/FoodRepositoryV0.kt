package com.pius.concurrency.pet.v0

import org.springframework.data.jpa.repository.JpaRepository

interface FoodRepositoryV0 : JpaRepository<FoodEntityV0, Long> {

    fun findByPetId(petId: Long): FoodEntityV0?
}