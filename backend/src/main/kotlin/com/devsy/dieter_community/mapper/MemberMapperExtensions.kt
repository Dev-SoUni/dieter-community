package com.devsy.dieter_community.mapper

import com.devsy.dieter_community.domain.Member
import com.devsy.dieter_community.dto.MemberResponse

fun Member.toResponse(): MemberResponse =
    MemberResponse(
        email = this.email,
        nickname = this.nickname,
    )