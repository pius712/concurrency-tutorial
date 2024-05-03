package com.pius.concurrency.pet.v3

import com.pius.concurrency.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "pet_v2")
data class PetEntityV3(
    var power: Long,
) : BaseEntity() {
}