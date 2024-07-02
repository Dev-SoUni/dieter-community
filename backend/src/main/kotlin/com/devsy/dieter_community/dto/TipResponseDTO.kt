package com.devsy.dieter_community.dto

import java.time.LocalDateTime

data class TipResponseDTO(
    val id: String,
    val title: String,
    val content: String,
    val updatedAt: LocalDateTime,
    val createdAt: LocalDateTime,
)
