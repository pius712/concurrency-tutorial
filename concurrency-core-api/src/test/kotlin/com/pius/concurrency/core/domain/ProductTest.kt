package com.pius.concurrency.core.domain

import com.pius.concurrency.core.domain.product.ProductWriter
import com.pius.concurrency.test.ProductRepository
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestConstructor


@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class ProductTest(
    private val productRepository: ProductRepository,
    private val productWriter: ProductWriter
) {

    @Test
    fun `테스트`() {
        productWriter.createProduct("product")
        productWriter.addItem(1, "modify product")
    }

}