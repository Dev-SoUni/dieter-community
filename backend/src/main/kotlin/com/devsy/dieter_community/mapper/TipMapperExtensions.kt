package com.devsy.dieter_community.mapper

import com.devsy.dieter_community.domain.Member
import com.devsy.dieter_community.domain.Tip
import com.devsy.dieter_community.dto.TipPostRequest
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

fun TipPostRequest.toDomain(writer: Member): Tip =
    Tip(
        title = this.title,
        writer = writer,
        content = this.content,
    )
