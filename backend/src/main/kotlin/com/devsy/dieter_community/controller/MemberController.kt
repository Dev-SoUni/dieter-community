package com.devsy.dieter_community.controller

import com.devsy.dieter_community.dto.MemberPostRequest
import com.devsy.dieter_community.dto.MemberResponse
import com.devsy.dieter_community.mapper.toDomain
import com.devsy.dieter_community.mapper.toResponse
import com.devsy.dieter_community.service.MemberService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/members")
class MemberController(
    private val memberService: MemberService,
) {

    @PostMapping
    fun postMember(
        @RequestBody @Valid requestBody: MemberPostRequest,
    ): MemberResponse =
        memberService.post(requestBody.toDomain()).toResponse()
}