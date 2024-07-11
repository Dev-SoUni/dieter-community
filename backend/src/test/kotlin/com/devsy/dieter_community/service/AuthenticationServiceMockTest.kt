package com.devsy.dieter_community.service

import com.devsy.dieter_community.domain.Member
import com.devsy.dieter_community.exception.CustomException
import com.devsy.dieter_community.repository.MemberRepository
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.springframework.security.crypto.password.PasswordEncoder

@ExtendWith(MockitoExtension::class)
class AuthenticationServiceMockTest {

    @Nested
    @DisplayName("회원 인증")
    inner class Authentication {

        @InjectMocks
        private lateinit var authenticationService: AuthenticationService

        @Mock
        private lateinit var passwordEncoder: PasswordEncoder

        @Mock
        private lateinit var tokenService: TokenService

        @Mock
        private lateinit var memberRepository: MemberRepository

        @Nested
        @DisplayName("성공 케이스")
        inner class SuccessCase {

            @Test
            @DisplayName("회원 인증")
            fun authentication() {
                // Given
                val email = "Lion@example.com"
                val nickname = "라이언"
                val password = "1234"

                // When
                findByEmailSuccessMocking(email, nickname, password)
                Mockito.`when`(passwordEncoder.matches(anyString(), anyString()))
                    .thenReturn(true)
                Mockito.`when`(tokenService.generate(any()))
                    .thenReturn("AccessToken")

                val authenticationResponse = authenticationService.authentication(email, password)

                // Then
                assertThat(authenticationResponse).isNotNull()
                assertThat(authenticationResponse.email).isEqualTo(email)
                assertThat(authenticationResponse.nickname).isEqualTo(nickname)
            }
        }

        @Nested
        @DisplayName("실패 케이스")
        inner class FailCase {

            @Test
            @DisplayName("authentication : 존재하지 않는 Email")
            fun authenticationByEmail() {
                // Given
                val email = "Lion@example.com"
                val password = "1234"

                // When, Then
                Mockito.`when`(memberRepository.findByEmail(email)).thenReturn(null)

                assertThatThrownBy { authenticationService.authentication(email, password) }
                    .isInstanceOf(CustomException::class.java)
                    .withFailMessage("해당 이메일을 찾을 수 없습니다.")

            }

            @Test
            @DisplayName("authentication : 비밀번호 불일치")
            fun authenticationByPassword() {
                // Given
                val email = "Lion@example.com"
                val nickname = "라이언"
                val password = "1234"

                // When, Then
                findByEmailSuccessMocking(email, nickname, password)
                Mockito.`when`(
                    passwordEncoder.matches(anyString(), anyString())
                ).thenReturn(false)

                assertThatThrownBy { authenticationService.authentication(email, password) }
                    .isInstanceOf(CustomException::class.java)
                    .withFailMessage("비밀번호가 일치하지 않습니다.")
            }
        }

        private fun findByEmailSuccessMocking(
            email: String,
            nickname: String,
            password: String,
        ) {
            Mockito.`when`(memberRepository.findByEmail(email))
                .thenReturn(
                    Member(
                        email = email,
                        nickname = nickname,
                        password = password
                    )
                )
        }
    }
}