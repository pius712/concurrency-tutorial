package com.pius.concurrency.test

import com.pius.concurrency.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name = "product")
data class ProductEntity(
    val name: String,

    @OneToMany(
        cascade = [jakarta.persistence.CascadeType.ALL],
    )
    @JoinColumn(name = "product_id")
    val items: MutableList<ItemEntity>,


    @Version
    val version: Long = 0

) : BaseEntity() {

}
