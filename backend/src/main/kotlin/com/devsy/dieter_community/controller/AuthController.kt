package com.devsy.dieter_community.controller

import com.devsy.dieter_community.domain.Member
import com.devsy.dieter_community.dto.AuthenticateResponse
import com.devsy.dieter_community.dto.AuthenticationRequest
import com.devsy.dieter_community.dto.MemberResponse
import com.devsy.dieter_community.exception.CustomException
import com.devsy.dieter_community.service.AuthenticationService
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.DisabledException
import org.springframework.security.authentication.LockedException
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

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
        response: HttpServletResponse,
        @RequestBody requestBody: AuthenticationRequest,
    ): AuthenticateResponse =
        runCatching {
            val authenticationResponse = authenticationService.authentication(
                requestBody.email,
                requestBody.password,
            )

            response.addCookie(authenticationResponse.refreshTokenCookie)

            return AuthenticateResponse(
                email = authenticationResponse.email,
                nickname = authenticationResponse.nickname,
                accessToken = authenticationResponse.accessToken,
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

    @PostMapping("/logout")
    fun logout(response: HttpServletResponse): ResponseEntity<Unit> {
        val cookie = authenticationService.createExpiredRefreshTokenCookie()

        response.addCookie(cookie)

        return ResponseEntity
            .noContent()
            .build()
    }
}