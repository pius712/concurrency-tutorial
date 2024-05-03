package com.pius.concurrency.pet.v1

import com.pius.concurrency.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "pet")
data class PetEntityV1(
    var power: Long,
) : BaseEntity() {
}