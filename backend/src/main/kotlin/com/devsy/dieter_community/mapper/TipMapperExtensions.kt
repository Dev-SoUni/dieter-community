package com.devsy.dieter_community.mapper

import com.devsy.dieter_community.domain.Tip
import com.devsy.dieter_community.dto.TipResponse

fun Tip.toResponse(): TipResponse =
    TipResponse(
        id = this.id!!,
        title = this.title,
        writer = this.writer.toResponse(),
        content = this.content,
        updatedAt = this.updatedAt,
        createdAt = this.createdAt,
    )