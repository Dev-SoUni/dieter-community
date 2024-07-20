package com.devsy.dieter_community.controller

import com.devsy.dieter_community.domain.Member
import com.devsy.dieter_community.dto.TipLikePostRequest
import com.devsy.dieter_community.dto.TipLikeResponse
import com.devsy.dieter_community.exception.CustomException
import com.devsy.dieter_community.mapper.toResponse
import com.devsy.dieter_community.service.TipLikeService
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/tip-likes")
class TipLikeController(
    val tipLikeService: TipLikeService,
) {

    @GetMapping("/auth")
    fun getTipLikeByTipAndMember(
        @AuthenticationPrincipal member: Member,
        @RequestParam tipId: String,
    ): TipLikeResponse =
        tipLikeService.getByTipAndMember(tipId = tipId, member = member)
            ?.toResponse()
            ?: throw CustomException(HttpStatus.NO_CONTENT, "")

    @PostMapping("")
    fun postTipLike(
        @AuthenticationPrincipal member: Member,
        @RequestBody requestBody: TipLikePostRequest,
    ): TipLikeResponse =
        tipLikeService.post(tipId = requestBody.tipId, member = member)
            ?.toResponse()
            ?: throw CustomException(HttpStatus.BAD_REQUEST, "꿀팁 좋아요 등록에 실패했습니다.")

}