package com.devsy.dieter_community.filter

import com.devsy.dieter_community.service.CustomUserDetailsService
import com.devsy.dieter_community.service.TokenService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter(
    private val tokenService: TokenService,
    private val userDetailsService: CustomUserDetailsService,
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        if (request.requestURI == "/api/auth/refresh") {
            filterChain.doFilter(request, response)
            return
        }

        val authorization = request.getHeader("Authorization")

        if (authorization.doesNotContainBearerToken()) {
            filterChain.doFilter(request, response)
            return
        }

        val accessToken = authorization.extractToken()
        val email = tokenService.extractEmail(accessToken)

        if (email != null) {
            val userDetails = userDetailsService.loadUserByUsername(email)

            if (tokenService.isValid(accessToken, userDetails)) {
                updateContext(request, userDetails)
            }

            filterChain.doFilter(request, response)
        }
    }

    private fun String?.doesNotContainBearerToken() = this == null || !this.startsWith("Bearer ")

    private fun String.extractToken() = this.substringAfter("Bearer ")

    private fun updateContext(
        request: HttpServletRequest,
        userDetails: UserDetails,
    ) {
        val authentication = UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
        authentication.details = WebAuthenticationDetailsSource().buildDetails(request)

        SecurityContextHolder.getContext().authentication = authentication
    }
}
