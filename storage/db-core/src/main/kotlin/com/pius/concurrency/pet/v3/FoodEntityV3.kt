package com.pius.concurrency.pet.v3

import com.pius.concurrency.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.Table
import jakarta.persistence.Version

@Entity
@Table(name = "food_v2")
data class FoodEntityV3(
    val petId: Long,
    var count: Long,
    @Version
    var version: Long = 0
) : BaseEntity() {
}