package com.devsy.dieter_community.controller

import com.devsy.dieter_community.domain.Member
import com.devsy.dieter_community.dto.TipPatchRequest
import com.devsy.dieter_community.dto.TipPostRequest
import com.devsy.dieter_community.dto.TipResponse
import com.devsy.dieter_community.exception.CustomException
import com.devsy.dieter_community.exception.ErrorCode
import com.devsy.dieter_community.mapper.toDomain
import com.devsy.dieter_community.mapper.toResponse
import com.devsy.dieter_community.service.TipLikeService
import com.devsy.dieter_community.service.TipService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/tips")
class TipController(
    val tipService: TipService,
    val tipLikeService: TipLikeService,
) {

    @GetMapping("")
    fun getTips(
        @PageableDefault(page = 0, size = 10, sort = ["createdAt"], direction = Sort.Direction.DESC) pageable: Pageable,
        @RequestParam(name = "title", defaultValue = "", required = false) title: String,
        @RequestParam(name = "page", defaultValue = "0", required = false) page: Int
    ): Page<TipResponse> =
        tipService
            .findByTitle(pageable, title)
            .map { it.toResponse() }

    @GetMapping("/{id}")
    fun getTip(
        @PathVariable(name = "id") id: String,
    ): TipResponse =
        tipService.findById(id)
            ?.toResponse()
            ?: throw CustomException(ErrorCode.TIP_NOT_FOUND)

    @PostMapping("")
    fun postTip(
        @AuthenticationPrincipal member: Member,
        @RequestBody requestBody: TipPostRequest,
    ): TipResponse =
        tipService.post(
            requestBody.toDomain(writer = member),
        )
            ?.toResponse()
            ?: throw CustomException(ErrorCode.TIP_POST_ERROR)

    @PatchMapping("/{id}")
    fun patchTip(
        @PathVariable(name = "id") id: String,
        @RequestBody requestBody: TipPatchRequest,
    ): TipResponse =
        tipService.patch(
            id = id,
            title = requestBody.title,
            content = requestBody.content,
        )
            ?.toResponse()
            ?: throw CustomException(ErrorCode.TIP_PATCH_ERROR)

    @DeleteMapping("/{id}")
    fun deleteTip(
        @PathVariable(name = "id") id: String,
    ): ResponseEntity<Boolean> {
        val success = tipService.delete(id)

        return if (success)
            ResponseEntity.noContent().build()
        else
            throw CustomException(ErrorCode.TIP_DELETE_ERROR)
    }

    @DeleteMapping("/{id}/tip-likes")
    fun deleteTipLike(
        @AuthenticationPrincipal member: Member,
        @PathVariable(name = "id") id: String,
    ): ResponseEntity<Boolean> {
        val success = tipLikeService.deleteByTipAndMember(id, member)

        return if (success)
            ResponseEntity.noContent()
                .build()
        else
            throw CustomException(ErrorCode.TIP_LIKE_DELETE_ERROR)
    }
}
