package com.pius.concurrency.test

import com.pius.concurrency.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Table(name = "item")
@Entity
data class ItemEntity(
    var name: String,
) : BaseEntity()
