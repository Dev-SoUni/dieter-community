package com.devsy.dieter_community.providers

import com.devsy.dieter_community.domain.Member
import java.time.LocalDateTime
import java.util.*

fun generateMember(
    email: String,
    nickname: String,
): Member =
    Member(
        email = email,
        nickname = nickname,
        password = "",
    ).apply {
        this.id = UUID.randomUUID().toString()
        this.updatedAt = LocalDateTime.now()
        this.updatedAt = LocalDateTime.now()
    }
