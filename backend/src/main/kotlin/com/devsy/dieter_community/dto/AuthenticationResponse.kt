package com.devsy.dieter_community.dto

data class AuthenticationResponse(
    val email: String,
    val nickname: String,
    val accessToken: String,
)