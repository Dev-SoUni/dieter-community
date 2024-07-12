package com.devsy.dieter_community.service

import com.devsy.dieter_community.domain.Member
import com.devsy.dieter_community.exception.CustomException
import com.devsy.dieter_community.repository.MemberRepository
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.security.crypto.password.PasswordEncoder

@ExtendWith(MockitoExtension::class)
class MemberServiceMockTest {

    @InjectMocks
    private lateinit var memberService: MemberService

    @Mock
    private lateinit var passwordEncoder: PasswordEncoder

    @Mock
    private lateinit var memberRepository: MemberRepository

    @Nested
    @DisplayName("성공 케이스")
    inner class SuccessCase {

        @Test
        @DisplayName("post() : 성공 케이스")
        fun post() {
            // Given
            val member = generateMember()

            // When
            Mockito.`when`(
                memberRepository.findByEmail(member.email)
            ).thenReturn(null)
            Mockito.`when`(
                passwordEncoder.encode(member.password)
            ).thenReturn("")
            Mockito.`when`(
                memberRepository.save(member)
            ).thenReturn(member)

            val saveMember = memberService.post(member)

            // Then
            assertThat(saveMember).isNotNull()
            assertThat(saveMember).isEqualTo(member)

        }

    }

    @Nested
    @DisplayName("실패 케이스")
    inner class FailCase {

        @Test
        @DisplayName("post() : 사용 중인 이메일")
        fun postByEmail() {
            // Given
            val member = generateMember()

            // When, Then
            Mockito.`when`(
                memberRepository.findByEmail(member.email)
            ).thenReturn(member)

            assertThatThrownBy { memberService.post(member) }
                .isInstanceOf(CustomException::class.java)
                .withFailMessage("해당 이메일은 사용 중입니다.")
        }

        @Test
        @DisplayName("post() : 회원 저장 실패")
        fun postBySave() {
            // Given
            val member = generateMember()

            // When, Then
            Mockito.`when`(
                memberRepository.findByEmail(member.email)
            ).thenReturn(null)
            Mockito.`when`(passwordEncoder.encode(member.password))
                .thenReturn("")
            Mockito.`when`(
                memberRepository.save(member)
            ).thenThrow(CustomException::class.java)

            assertThatThrownBy { memberService.post(member) }
                .isInstanceOf(CustomException::class.java)
                .withFailMessage("회원가입에 실패했습니다.")
        }
    }

    private fun generateMember() =
        Member(
            email = "Lion@example.com",
            nickname = "라이언",
            password = "1234",
        )
}