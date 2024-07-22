package com.devsy.dieter_community.providers

import com.devsy.dieter_community.domain.Member
import com.devsy.dieter_community.service.TokenService
import java.util.*

fun createAccessToken(
    member: Member,
    tokenService: TokenService,
) =
    tokenService.generate(
        userDetails = member,
        expirationDate = Date(System.currentTimeMillis() + 3600000),
    )