package com.devsy.dieter_community.exception

import org.springframework.http.HttpStatus

class CustomException(code: ErrorCode) : RuntimeException() {
    val status: HttpStatus = code.status
    override val message: String = code.message
}