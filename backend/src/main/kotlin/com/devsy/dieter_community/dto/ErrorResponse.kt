package com.devsy.dieter_community.dto

data class ErrorResponse(
    val code: String,
    val status: Int,
    val message: String?,
)
