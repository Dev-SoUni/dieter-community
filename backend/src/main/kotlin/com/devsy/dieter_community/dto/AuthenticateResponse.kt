package com.devsy.dieter_community.dto

data class AuthenticateResponse(
    val email: String,
    val nickname: String,
    val accessToken: String,
)
