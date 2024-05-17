package com.pius.concurrency.pet.v5

import org.springframework.data.jpa.repository.JpaRepository

interface PetRepositoryV5 : JpaRepository<PetEntityV5, Long> {
}