package com.devsy.dieter_community.dto

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime

data class TipLikeResponse(
    val id: String,
    val tip: TipResponse,
    val member: MemberResponse,
    @JsonFormat(pattern = "yyyy.MM.dd") val createdAt: LocalDateTime,
)
