package com.pius.concurrency.pet.v1

import org.springframework.data.jpa.repository.JpaRepository

interface FoodRepository: JpaRepository<FoodEntity, Long>{
    fun findByPetId(petId:Long): FoodEntity?
}