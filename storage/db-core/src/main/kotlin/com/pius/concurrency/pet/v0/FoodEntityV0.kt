package com.pius.concurrency.pet.v0

import com.pius.concurrency.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "food")
data class FoodEntityV0(
    val petId: Long,
    var count: Long,
) : BaseEntity() {
}