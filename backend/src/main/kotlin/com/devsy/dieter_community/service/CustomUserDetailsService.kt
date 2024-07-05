package com.devsy.dieter_community.service

import com.devsy.dieter_community.repository.MemberRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(
    private val memberRepository: MemberRepository,
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        return memberRepository.findByEmail(username) ?: throw UsernameNotFoundException("해당 이메일(${username})의 회원 정보를 찾을 수 없습니다.")
    }

}