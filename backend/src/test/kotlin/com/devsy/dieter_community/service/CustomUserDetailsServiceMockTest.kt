package com.devsy.dieter_community.service

import com.devsy.dieter_community.domain.Member
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
import org.springframework.security.core.userdetails.UsernameNotFoundException

@ExtendWith(MockitoExtension::class)
class CustomUserDetailsServiceMockTest {

    @InjectMocks
    private lateinit var customUserDetailsService: CustomUserDetailsService

    @Mock
    private lateinit var memberRepository: MemberRepository

    @Nested
    @DisplayName("회원 조회 : 이메일로 조회")
    inner class LoadUserByUsername {

        @Nested
        @DisplayName("성공 케이스")
        inner class SuccessCase {

            @Test
            @DisplayName("loadUserByUsername() : 회원 조회 성공")
            fun loadUserByUsername() {
                // Given
                val email = "Lion@example.com"

                // When
                Mockito.`when`(
                    memberRepository.findByEmail(email)
                ).thenReturn(
                    Member(
                        email = email,
                        nickname = "",
                        password = "",
                    )
                )

                val member = customUserDetailsService.loadUserByUsername(email)

                // Then
                assertThat(member).isNotNull()
                assertThat(member.email).isEqualTo(email)
            }

        }

        @Nested
        @DisplayName("실패 케이스")
        inner class FailCase {

            @Test
            @DisplayName("loadUserByUsername() : 회원 조회 실패")
            fun loadUserByUsername() {
                // Given
                val email = "Lion@example.com"

                // When, Then
                Mockito.`when`(
                    memberRepository.findByEmail(email)
                ).thenReturn(null)

                assertThatThrownBy { customUserDetailsService.loadUserByUsername(email) }
                    .isInstanceOf(UsernameNotFoundException::class.java)
                    .withFailMessage("해당 이메일($email)의 회원 정보를 찾을 수 없습니다.")
            }
        }
    }
}