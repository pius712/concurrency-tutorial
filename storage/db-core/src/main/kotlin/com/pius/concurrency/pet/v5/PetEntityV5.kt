package com.pius.concurrency.pet.v5

import com.pius.concurrency.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "pet_v5")
data class PetEntityV5(
    var power: Long,
) : BaseEntity() {
}