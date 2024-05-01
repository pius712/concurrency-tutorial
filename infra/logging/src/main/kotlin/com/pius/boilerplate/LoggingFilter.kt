package com.pius.boilerplate

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.slf4j.MDC
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.util.*


@Component
internal class LoggingFilter(
    private val testService: TestClass
) : OncePerRequestFilter() {

    val log = LoggerFactory.getLogger(javaClass)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        testService.testMethod()
        val uuid = UUID.randomUUID()
        MDC.put("requestId", uuid.toString())
        log.info("once per request filter ${uuid.toString()}")
        filterChain.doFilter(request, response)
        MDC.clear()
    }

    private fun getHeader(request: HttpServletRequest, key: String) = request.getHeader(key) ?: "unknown"

}