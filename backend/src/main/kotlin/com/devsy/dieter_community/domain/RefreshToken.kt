package com.devsy.dieter_community.domain

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash

@RedisHash(value = "refreshToken", timeToLive = 86400000)
data class RefreshToken(
    @Id
    val id: String,
    val refreshToken: String,
)
