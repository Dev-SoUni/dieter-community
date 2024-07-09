package com.devsy.dieter_community.dto

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime

data class TipResponse(
    val id: String,
    val title: String,
    val content: String,
    val writer: MemberResponse,
    @JsonFormat(pattern = "yyy.MM.dd") val updatedAt: LocalDateTime,
    @JsonFormat(pattern = "yyy.MM.dd") val createdAt: LocalDateTime,
)
