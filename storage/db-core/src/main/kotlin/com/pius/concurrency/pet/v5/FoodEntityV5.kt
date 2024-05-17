package com.pius.concurrency.pet.v5

import com.pius.concurrency.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "food_v5")
data class FoodEntityV5(
    val petId: Long,
    var count: Long,
) : BaseEntity() {
}