package com.devsy.dieter_community.service

import com.devsy.dieter_community.domain.Member
import com.devsy.dieter_community.exception.CustomException
import com.devsy.dieter_community.exception.ErrorCode
import com.devsy.dieter_community.repository.MemberRepository
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
            throw CustomException(ErrorCode.USER_EMAIL_ALREADY_EXISTS)
        }

        member.encodePassword(passwordEncoder)

        return runCatching {
            memberRepository.save(member)
        }.getOrElse {
            throw CustomException(ErrorCode.USER_POST_ERROR)
        }
    }
}