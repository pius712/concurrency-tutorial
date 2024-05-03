package com.pius.concurrency.pet.v1

import org.springframework.data.jpa.repository.JpaRepository

interface PetRepository: JpaRepository<PetEntity, Long> {
}