package com.pius.concurrency.pet.v4

import org.springframework.data.jpa.repository.JpaRepository

interface FoodRepositoryV4 : JpaRepository<FoodEntityV4, Long> {

    fun findByPetId(petId: Long): FoodEntityV4?
}