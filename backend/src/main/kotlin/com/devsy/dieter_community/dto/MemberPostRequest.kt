package com.devsy.dieter_community.dto

data class MemberPostRequest(
    val email: String,
    val nickname: String,
    val password: String,
)