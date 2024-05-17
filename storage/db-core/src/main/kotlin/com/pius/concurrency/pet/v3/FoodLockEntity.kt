package com.pius.concurrency.pet.v3

import com.pius.concurrency.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.Index
import jakarta.persistence.Table

@Entity
@Table(name = "food_lock", indexes = [Index(columnList = "lockKey", unique = true)])
data class FoodLockEntity(
    val lockKey: String
) : BaseEntity()
