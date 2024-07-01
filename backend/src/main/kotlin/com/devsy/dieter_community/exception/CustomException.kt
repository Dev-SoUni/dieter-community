package com.devsy.dieter_community.exception

import org.springframework.http.HttpStatus

class CustomException(
    val status: HttpStatus,
    override val message: String,
) : RuntimeException()