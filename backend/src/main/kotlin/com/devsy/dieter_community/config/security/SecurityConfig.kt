package com.devsy.dieter_community.config.security

import com.devsy.dieter_community.exception.CustomAccessDeniedHandler
import com.devsy.dieter_community.exception.CustomAuthenticationEntryPoint
import com.devsy.dieter_community.filter.JwtAuthenticationFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

// Spring Security
//
// Kotlin 설정 문서: https://docs.spring.io/spring-security/reference/servlet/configuration/kotlin.html

@Configuration
@EnableWebSecurity // 스프링 시큐리티 필터가 스프링 필터체인에 등록되도록 하는 어노테이션
class SecurityConfig {

    private val customAuthenticationEntryPoint = CustomAuthenticationEntryPoint()
    private val customAccessDeniedHandler = CustomAccessDeniedHandler()

    @Bean
    fun passwordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun filterChain(
        http: HttpSecurity,
        jwtAuthenticationFilter: JwtAuthenticationFilter,
    ): SecurityFilterChain {
        http {
            csrf { disable() }
            cors { }
            authorizeRequests {
                authorize(HttpMethod.POST, "/api/tips", authenticated)
                authorize("/api/**", permitAll)
            }
            sessionManagement {
                sessionCreationPolicy = SessionCreationPolicy.STATELESS
            }
            exceptionHandling {
                authenticationEntryPoint = customAuthenticationEntryPoint
                accessDeniedHandler = customAccessDeniedHandler
            }
        }

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)

        return http.build()

    }
}