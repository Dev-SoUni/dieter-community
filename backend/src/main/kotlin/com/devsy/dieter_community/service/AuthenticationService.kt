package com.devsy.dieter_community.service

import com.devsy.dieter_community.config.JwtProperties
import com.devsy.dieter_community.domain.RefreshToken
import com.devsy.dieter_community.dto.AuthenticationResponse
import com.devsy.dieter_community.exception.CustomException
import com.devsy.dieter_community.exception.ErrorCode
import com.devsy.dieter_community.repository.RefreshTokenRepository
import jakarta.servlet.http.Cookie
import org.springframework.data.repository.findByIdOrNull
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
    private val customUserDetailsService: CustomUserDetailsService,
    private val refreshTokenRepository: RefreshTokenRepository,
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

        val redis = RefreshToken(id = email, refreshToken = refreshToken)
        refreshTokenRepository.save(redis)

        return AuthenticationResponse(
            email = user.email,
            nickname = user.nickname,
            accessToken = accessToken,
            refreshTokenCookie = refreshTokenCookie,
        )
    }

    fun refresh(refreshToken: String): String? {
        val extractedEmail = tokenService.extractEmail(refreshToken)

        return extractedEmail?.let { email ->
            // Redis에서 해당 email의 RefreshToken 조회
            val redisRefreshToken = refreshTokenRepository.findByIdOrNull(id = email)
                ?: throw CustomException(ErrorCode.REFRESH_TOKEN_NOT_FOUND)

            // Redis Token과 Refresh Token 일치 여부 확인
            if (redisRefreshToken.refreshToken != refreshToken)
                throw CustomException(ErrorCode.REFRESH_TOKEN_NOT_FOUND)

            val currentUserDetails = customUserDetailsService.loadUserByUsername(username = email)

            // 새로운 refreshToken 추가
            createAccessToken(currentUserDetails)
        }
    }

    fun deleteRedisRefreshToken(id: String) {
        val token = refreshTokenRepository.findByIdOrNull(id)

        if (token != null) {
            refreshTokenRepository.delete(token)
        }
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

    fun createExpiredRefreshTokenCookie(): Cookie =
        Cookie("refreshToken", "").apply {
            path = "/"
            isHttpOnly = true
            secure = true
            maxAge = 0
        }

    private fun getAccessTokenExpiration(): Date =
        Date(System.currentTimeMillis() + jwtProperties.accessTokenExpiration)

    private fun getRefreshTokenExpiration(): Date =
        Date(System.currentTimeMillis() + jwtProperties.refreshTokenExpiration)
}
