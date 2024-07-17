package com.devsy.dieter_community.config.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

// Spring Security CORS
//
// Kotlin 설정 문서: https://docs.spring.io/spring-security/reference/reactive/integrations/cors.html

@Configuration
class CORSConfig {

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration()
        configuration.allowedOrigins = listOf("http://localhost:5173")
        configuration.allowedHeaders = listOf("Authorization", "Content-Type")
        configuration.allowedMethods = listOf("GET", "POST", "PUT", "PATCH", "DELETE")
        configuration.allowCredentials = true

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/api/**", configuration)

        return source
    }

}