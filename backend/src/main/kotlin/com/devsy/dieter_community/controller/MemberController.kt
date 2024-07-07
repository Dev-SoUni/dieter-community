package com.devsy.dieter_community.controller

import com.devsy.dieter_community.dto.MemberPostRequest
import com.devsy.dieter_community.dto.MemberResponse
import com.devsy.dieter_community.service.MemberService
import org.springframework.web.bind.annotation.GetMapping
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
        @RequestBody requestBody: MemberPostRequest
    ): MemberResponse {
        val postedMember = memberService.post(
            requestBody.email,
            requestBody.nickname,
            requestBody.password,
        )

        return MemberResponse(
            email = postedMember.email,
            nickname = postedMember.nickname,
        )
    }
}