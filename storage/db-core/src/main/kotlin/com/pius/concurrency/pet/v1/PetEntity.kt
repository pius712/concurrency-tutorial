package com.pius.concurrency.pet.v1

import com.pius.concurrency.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "pet")
data class PetEntity(
    var power: Long,
) : BaseEntity() {
}