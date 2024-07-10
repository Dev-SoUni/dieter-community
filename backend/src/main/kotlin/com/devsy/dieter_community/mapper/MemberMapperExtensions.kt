package com.devsy.dieter_community.mapper

import com.devsy.dieter_community.domain.Member
import com.devsy.dieter_community.dto.MemberPostRequest
import com.devsy.dieter_community.dto.MemberResponse

fun Member.toResponse(): MemberResponse =
    MemberResponse(
        email = this.email,
        nickname = this.nickname,
    )

fun MemberPostRequest.toDomain(): Member =
    Member(
        email = this.email,
        nickname = this.nickname,
        password = this.password,
    )