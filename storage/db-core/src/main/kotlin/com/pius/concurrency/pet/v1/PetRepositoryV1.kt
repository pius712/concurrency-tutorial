package com.pius.concurrency.pet.v1

import org.springframework.data.jpa.repository.JpaRepository

interface PetRepositoryV1 : JpaRepository<PetEntityV1, Long> {
}