package com.devsy.dieter_community.mapper

import com.devsy.dieter_community.domain.TipLike
import com.devsy.dieter_community.dto.TipLikeResponse

fun TipLike.toResponse(): TipLikeResponse =
    TipLikeResponse(
        id = this.id!!,
        tip = this.tip.toResponse(),
        member = this.member.toResponse(),
        createdAt = this.createdAt,
    )