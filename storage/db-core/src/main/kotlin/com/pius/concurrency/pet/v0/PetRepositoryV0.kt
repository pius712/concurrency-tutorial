package com.pius.concurrency.pet.v0

import org.springframework.data.jpa.repository.JpaRepository

interface PetRepositoryV0 : JpaRepository<PetEntityV0, Long> {
}