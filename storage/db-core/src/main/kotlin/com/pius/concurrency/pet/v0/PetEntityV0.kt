package com.pius.concurrency.pet.v0

import com.pius.concurrency.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "pet")
data class PetEntityV0(
    var power: Long,
) : BaseEntity() {
}