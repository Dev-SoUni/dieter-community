package com.devsy.dieter_community.dto

import com.devsy.dieter_community.domain.Tip
import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime

class TipResponseDTO(tip: Tip){
    val id: String = tip.id!!

    val title: String = tip.title

    val content: String = tip.content

    @JsonFormat(pattern = "yyy.MM.dd")
    val updatedAt: LocalDateTime = tip.updatedAt

    @JsonFormat(pattern = "yyy.MM.dd")
    val createdAt: LocalDateTime = tip.createdAt

}
