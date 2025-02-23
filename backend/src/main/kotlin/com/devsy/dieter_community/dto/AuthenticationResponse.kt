package com.devsy.dieter_community.dto

import jakarta.servlet.http.Cookie

data class AuthenticationResponse(
    val email: String,
    val nickname: String,
    val accessToken: String,
    val refreshTokenCookie: Cookie,
)