package com.pius.concurrency.core.domain.product

import com.pius.concurrency.test.ItemEntity
import com.pius.concurrency.test.ProductEntity
import com.pius.concurrency.test.ProductRepository
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class ProductWriter(
    private val productRepository: ProductRepository,
) {

    @Transactional
    fun createProduct(name: String): ProductEntity {
        val itemEntity = ItemEntity("item")

        return productRepository.save(ProductEntity(name, mutableListOf(itemEntity)))
    }

    @Transactional
    fun addItem(id: Long, name: String) {
        val product = productRepository.findByIdOrNull(id) ?: throw RuntimeException("Product not found")
        val itemEntity = ItemEntity(name)
        product.items.add(itemEntity)
    }


    @Transactional
    fun updateItemName(id: Long, name: String) {
        val product = productRepository.findByIdOrNull(id) ?: throw RuntimeException("Product not found")
        val itemEntity = product.items[0]
        itemEntity.name = "new item"
    }
}