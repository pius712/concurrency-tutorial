package com.pius.concurrency.pet.v2

import com.pius.concurrency.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "pet_v2")
data class PetEntityV2(
    var power: Long,
) : BaseEntity() {
}