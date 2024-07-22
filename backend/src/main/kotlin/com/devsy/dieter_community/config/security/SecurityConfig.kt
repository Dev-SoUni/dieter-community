package com.devsy.dieter_community.config.security

import com.devsy.dieter_community.exception.CustomAccessDeniedHandler
import com.devsy.dieter_community.exception.CustomAuthenticationEntryPoint
import com.devsy.dieter_community.filter.JwtAuthenticationFilter
import com.devsy.dieter_community.filter.JwtExceptionFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
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
    fun authenticationManager(config: AuthenticationConfiguration): AuthenticationManager =
        config.authenticationManager

    @Bean
    fun passwordEncoder(): PasswordEncoder =
        BCryptPasswordEncoder()

    @Bean
    fun filterChain(
        http: HttpSecurity,
        jwtExceptionFilter: JwtExceptionFilter,
        jwtAuthenticationFilter: JwtAuthenticationFilter,
    ): SecurityFilterChain {
        http {
            csrf { disable() }
            cors { }
            authorizeRequests {
                authorize("/api/auth/refresh", permitAll)
                authorize("/api/auth/logout", authenticated)
                authorize(HttpMethod.POST, "/api/tips", authenticated)
                authorize(HttpMethod.PATCH, "/api/tips/**", authenticated)
                authorize(HttpMethod.DELETE, "/api/tips/**", authenticated)
                authorize(HttpMethod.GET, "/api/tip-likes", authenticated)
                authorize(HttpMethod.POST, "/api/tip-likes", authenticated)
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

        http
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
            .addFilterBefore(jwtExceptionFilter, JwtAuthenticationFilter::class.java)

        return http.build()
    }
}
