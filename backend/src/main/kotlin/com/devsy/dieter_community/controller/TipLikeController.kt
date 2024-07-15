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

    @PostMapping("")
    fun postTipLike(
        @AuthenticationPrincipal member: Member,
        @RequestBody requestBody: TipLikePostRequest,
    ): TipLikeResponse =
        tipLikeService.post(tipId = requestBody.tipId, member = member)
            ?.toResponse()
            ?: throw CustomException(HttpStatus.BAD_REQUEST, "꿀팁 좋아요 등록에 실패했습니다.")


    @DeleteMapping("/{id}")
    fun deleteTipLike(
        @PathVariable(name = "id") id: String,
    ): Boolean {
        val success = tipLikeService.delete(id)

        return if (success)
            true
        else
            throw CustomException(HttpStatus.BAD_REQUEST, "꿀팁 좋아요 해제에 실패했습니다.")

    }
}