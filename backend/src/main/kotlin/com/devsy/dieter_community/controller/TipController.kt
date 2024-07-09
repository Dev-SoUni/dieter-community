package com.devsy.dieter_community.controller

import com.devsy.dieter_community.domain.Member
import com.devsy.dieter_community.domain.Tip
import com.devsy.dieter_community.dto.TipPatchDTO
import com.devsy.dieter_community.dto.TipPostDTO
import com.devsy.dieter_community.dto.TipResponse
import com.devsy.dieter_community.exception.CustomException
import com.devsy.dieter_community.service.TipService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/tips")
class TipController(
    val tipService: TipService,
) {

    @GetMapping("")
    fun getTips(
        @PageableDefault(page = 0, size = 10, sort = ["createdAt"], direction = Sort.Direction.DESC) pageable: Pageable,
        @RequestParam(name = "title", defaultValue = "", required = false) title: String,
        @RequestParam(name = "page", defaultValue = "0", required = false) page: Int
    ): ResponseEntity<Page<TipResponse>> {
        return ResponseEntity.ok(
            tipService
                .findByTitle(pageable, title)
                .map {
                    TipResponse(it)
                }
        )
    }

    @GetMapping("/{id}")
    fun getTip(
        @PathVariable(name = "id") id: String,
    ): ResponseEntity<TipResponse> {
        val tip = tipService.findById(id) ?: throw CustomException(HttpStatus.NOT_FOUND, "해당 꿀팁을 찾을 수 없습니다.")
        return ResponseEntity.ok(
            TipResponse(tip)
        )
    }

    @PostMapping("")
    fun postTip(
        @AuthenticationPrincipal member: Member,
        @RequestBody requestBody: TipPostDTO,
    ): ResponseEntity<Tip> {
        print("member $member")
        val postedTip = tipService.post(
            title = requestBody.title,
            writer = member,
            content = requestBody.content,
        )

        return ResponseEntity.ok(postedTip)
    }

    @PatchMapping("/{id}")
    fun patchTip(
        @PathVariable(name = "id") id: String,
        @RequestBody requestBody: TipPatchDTO,
    ): ResponseEntity<Tip> {
        val patchedTip = tipService.patch(
            id = id,
            title = requestBody.title,
            content = requestBody.content,
        )

        return ResponseEntity.ok(patchedTip)
    }

    @DeleteMapping("/{id}")
    fun deleteTip(
     @PathVariable(name = "id") id: String
    ): ResponseEntity<HttpStatus> {
        tipService.delete(id)

        return ResponseEntity(HttpStatus.NO_CONTENT)
    }
}