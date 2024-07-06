package com.devsy.dieter_community.service

import com.devsy.dieter_community.domain.Member
import com.devsy.dieter_community.exception.CustomException
import com.devsy.dieter_community.repository.MemberRepository
import org.springframework.http.HttpStatus
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class MemberService(
    private val passwordEncoder: PasswordEncoder,
    private val memberRepository: MemberRepository,
) {

    fun post(
        email: String,
        nickname: String,
        password: String,
    ): Member {
        memberRepository.findByEmail(email) ?: CustomException(HttpStatus.BAD_REQUEST, "해당 이메일은 사용 중입니다.")

        val member = Member(
            email = email,
            nickname = nickname,
            password = passwordEncoder.encode(password)
        )
        return memberRepository.save(member)
    }
}