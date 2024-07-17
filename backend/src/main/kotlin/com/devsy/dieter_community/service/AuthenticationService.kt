package com.devsy.dieter_community.service

import com.devsy.dieter_community.config.JwtProperties
import com.devsy.dieter_community.dto.AuthenticationResponse
import jakarta.servlet.http.Cookie
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.util.*

@Service
class AuthenticationService(
    private val authManager: AuthenticationManager,
    private val userDetailsService: CustomUserDetailsService,
    private val tokenService: TokenService,
    private val jwtProperties: JwtProperties,
) {

    fun authentication(
        email: String,
        password: String,
    ): AuthenticationResponse {
        authManager.authenticate(
            UsernamePasswordAuthenticationToken(email, password)
        )

        val user = userDetailsService.loadUserByUsername(email)
        val accessToken = createAccessToken(user)
        val refreshToken = createRefreshToken(user)
        val refreshTokenCookie = createRefreshTokenCookie(refreshToken)

        return AuthenticationResponse(
            email = user.email,
            nickname = user.nickname,
            accessToken = accessToken,
            refreshTokenCookie = refreshTokenCookie,
        )
    }

    private fun createAccessToken(user: UserDetails): String = tokenService.generate(
        userDetails = user,
        expirationDate = getAccessTokenExpiration(),
    )

    private fun createRefreshToken(user: UserDetails): String = tokenService.generate(
        userDetails = user,
        expirationDate = getRefreshTokenExpiration(),
    )

    private fun createRefreshTokenCookie(token: String): Cookie =
        Cookie("refreshToken", token).apply {
            path = "/"
            isHttpOnly = true
            secure = true
            maxAge = jwtProperties.accessTokenExpiration.toInt()
        }

    private fun getAccessTokenExpiration(): Date =
        Date(System.currentTimeMillis() + jwtProperties.accessTokenExpiration)

    private fun getRefreshTokenExpiration(): Date =
        Date(System.currentTimeMillis() + jwtProperties.refreshTokenExpiration)
}