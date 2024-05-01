package com.pius.boilerplate

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.slf4j.MDC
import org.springframework.web.servlet.HandlerInterceptor

class LoggingInterceptor : HandlerInterceptor {

    val log = LoggerFactory.getLogger(javaClass)
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        MDC.get("requestId")?.let {
            log.info("request id: $it")
        } ?: log.info("request id not found")

        return true
    }
}