package com.pius.concurrency.pet.v4

import com.pius.concurrency.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "pet_v4")
data class PetEntityV4(
    var power: Long,
) : BaseEntity() {
}