package com.devsy.dieter_community.controller

import com.devsy.dieter_community.domain.Member
import com.devsy.dieter_community.dto.AuthenticationRequest
import com.devsy.dieter_community.dto.AuthenticationResponse
import com.devsy.dieter_community.dto.MemberResponse
import com.devsy.dieter_community.exception.CustomException
import com.devsy.dieter_community.service.AuthenticationService
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.DisabledException
import org.springframework.security.authentication.LockedException
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authenticationService: AuthenticationService,
) {

    @GetMapping
    fun getLoginMember(
        @AuthenticationPrincipal member: Member
    ) = MemberResponse(
        email = member.email,
        nickname = member.nickname,
    )

    @PostMapping
    fun authenticate(
        @RequestBody requestBody: AuthenticationRequest,
    ): AuthenticationResponse =
        runCatching {
            authenticationService.authentication(
                requestBody.email,
                requestBody.password,
            )
        }
            .getOrElse { ex ->
                when (ex) {
                    is DisabledException ->
                        throw CustomException(
                            HttpStatus.BAD_REQUEST,
                            "해당 계정은 비활성화 상태입니다.",
                        )
                    is LockedException ->
                        throw CustomException(
                            HttpStatus.BAD_REQUEST,
                            "해당 계정은 잠금 상태입니다.",
                        )
                    is BadCredentialsException ->
                        throw CustomException(
                            HttpStatus.BAD_REQUEST,
                            "이메일 또는 비밀번호가 일치하지 않습니다.",
                        )
                    else ->
                        throw CustomException(
                            HttpStatus.INTERNAL_SERVER_ERROR,
                            "알 수 없는 오류가 발생했습니다."
                        )
                }
            }
}