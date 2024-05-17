package com.pius.concurrency.pet.v4

import com.pius.concurrency.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "food_v4")
data class FoodEntityV4(
    val petId: Long,
    var count: Long,
) : BaseEntity() {
}