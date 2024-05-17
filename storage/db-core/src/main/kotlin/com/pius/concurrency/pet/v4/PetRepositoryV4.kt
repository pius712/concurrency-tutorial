package com.pius.concurrency.pet.v4

import org.springframework.data.jpa.repository.JpaRepository

interface PetRepositoryV4 : JpaRepository<PetEntityV4, Long> {
}