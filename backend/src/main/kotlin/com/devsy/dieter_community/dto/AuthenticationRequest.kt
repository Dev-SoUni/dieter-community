package com.devsy.dieter_community.dto

data class AuthenticationRequest(
    val email: String,
    val password: String,
)