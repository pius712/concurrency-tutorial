package com.pius.boilerplate.client.example

import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestConstructor

@Tag("context")
@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class ExampleClientFailTest(
    private val exampleClient: ExampleClient
) {

    @Test
    fun `example client`() {
        val result = exampleClient.getExample()
        println(result)
    }
}