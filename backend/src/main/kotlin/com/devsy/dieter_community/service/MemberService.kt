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

    fun post(member: Member): Member {
        val found = memberRepository.findByEmail(member.email)

        if (found != null) {
            throw CustomException(HttpStatus.BAD_REQUEST, "해당 이메일은 사용 중입니다.")
        }

        member.encodePassword(passwordEncoder)

        return runCatching {
            memberRepository.save(member)
        }.getOrElse {
            throw CustomException(HttpStatus.BAD_REQUEST, "회원가입에 실패했습니다.")
        }
    }
}