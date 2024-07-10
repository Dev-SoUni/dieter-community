package com.devsy.dieter_community.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class MemberPostRequest(
    @field:Email(message = "이메일 형식이 올바르지 않습니다.")
    val email: String,

    @field:NotBlank(message = "닉네임은 필수 입력 사항입니다.")
    val nickname: String,

    @field:NotBlank(message = "비밀번호는 필수 입력 사항입니다.")
    val password: String,
)