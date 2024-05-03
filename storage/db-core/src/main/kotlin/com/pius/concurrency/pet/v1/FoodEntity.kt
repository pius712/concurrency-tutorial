package com.pius.concurrency.pet.v1

import com.pius.concurrency.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "food")
data class FoodEntity(
    val petId: Long,
    var count: Long,
) : BaseEntity() {
}