package com.devsy.dieter_community.dto

import com.fasterxml.jackson.annotation.JsonFormat
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime

data class TipResponseDTO(
    val id: String,
    val title: String,
    val content: String,
    @JsonFormat(pattern = "yyy.MM.dd")
    val updatedAt: LocalDateTime,
    @JsonFormat(pattern = "yyy.MM.dd")
    val createdAt: LocalDateTime,
)
