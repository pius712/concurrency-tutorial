package com.pius.boilerplate

import jakarta.servlet.Filter
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
internal class LoggingFilterConfig(
    private val loggingFilter: LoggingFilter,
    private val servletLoggingFilter: ServletLoggingFilter,
) : WebMvcConfigurer {

    @Bean
    fun loggingFilterBean(): FilterRegistrationBean<Filter> {
        val filterBean = FilterRegistrationBean<Filter>(
            loggingFilter
        )
        filterBean.addUrlPatterns("/*")
        filterBean.order = 1
        return filterBean
    }

    @Bean
    fun servletLoggingFilterBean(): FilterRegistrationBean<Filter> {
        val filterBean = FilterRegistrationBean<Filter>(
            servletLoggingFilter
        )

        filterBean.addUrlPatterns("/*")
        filterBean.order = 1
        return filterBean
    }

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(LoggingInterceptor())
    }
}