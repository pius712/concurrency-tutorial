package com.pius.concurrency.pet.v3

import org.springframework.data.jpa.repository.JpaRepository

interface PetRepositoryV3 : JpaRepository<PetEntityV3, Long> {
}