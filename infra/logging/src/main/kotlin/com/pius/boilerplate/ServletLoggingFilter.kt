package com.pius.boilerplate

import jakarta.servlet.Filter
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import org.springframework.stereotype.Component

@Component
class ServletLoggingFilter(
    private val testService: TestClass
) : Filter {
    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {
        testService.testMethod()
        chain?.doFilter(request, response)
    }
}