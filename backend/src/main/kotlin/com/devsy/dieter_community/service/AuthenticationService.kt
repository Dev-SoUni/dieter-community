package com.devsy.dieter_community.service

import com.devsy.dieter_community.dto.AuthenticationResponse
import com.devsy.dieter_community.exception.CustomException
import com.devsy.dieter_community.repository.MemberRepository
import org.springframework.http.HttpStatus
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthenticationService(
    private val passwordEncoder: PasswordEncoder,
    private val tokenService: TokenService,
    private val memberRepository: MemberRepository,
) {

    fun authentication(
        email: String,
        password: String,
    ): AuthenticationResponse {

        val member = memberRepository.findByEmail(email) ?: throw CustomException(HttpStatus.BAD_REQUEST, "해당 이메일을 찾을 수 없습니다.")

        if (!passwordEncoder.matches(password, member.password)) {
            throw CustomException(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다.")
        }

        val accessToken = tokenService.generate(member)

        return AuthenticationResponse(
            email = member.email,
            nickname = member.nickname,
            accessToken = accessToken
        )
    }
}