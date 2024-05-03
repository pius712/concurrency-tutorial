package com.pius.concurrency.pet.v2

import org.springframework.data.jpa.repository.JpaRepository

interface PetRepositoryV2 : JpaRepository<PetEntityV2, Long> {
}