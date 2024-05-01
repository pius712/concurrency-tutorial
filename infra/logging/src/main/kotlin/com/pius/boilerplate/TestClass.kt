package com.pius.boilerplate

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class TestClass {
    val log = LoggerFactory.getLogger(javaClass)
    fun testMethod() {
        log.info("TestClass")
    }
}