package com.pius.concurrency.pet.v5

import org.springframework.data.jpa.repository.JpaRepository

interface FoodRepositoryV5 : JpaRepository<FoodEntityV5, Long> {

    fun findByPetId(petId: Long): FoodEntityV5?
}