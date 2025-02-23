package com.devsy.dieter_community.controller

import com.devsy.dieter_community.domain.Member
import com.devsy.dieter_community.dto.AuthenticationRequest
import com.devsy.dieter_community.repository.RefreshTokenRepository
import com.devsy.dieter_community.service.AuthenticationService
import com.devsy.dieter_community.service.MemberService
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.transaction.Transactional
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import kotlin.test.Test

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
class AuthControllerTest {

    @Autowired
    private lateinit var mock: MockMvc

    @Autowired
    private lateinit var mapper: ObjectMapper

    @Autowired
    private lateinit var authenticationService: AuthenticationService

    @Autowired
    private lateinit var memberService: MemberService

    @Autowired
    private lateinit var refreshTokenRepository: RefreshTokenRepository

    @BeforeEach
    fun beforeEach() {
        val member = Member(
            email = "Lion@example.com",
            nickname = "사자",
            password = "12345678",
        )
        memberService.post(member)
    }

    @Nested
    @DisplayName("인증")
    inner class Authenticate {

        @Nested
        @DisplayName("성공 케이스")
        inner class SuccessCase {

            @Test
            @DisplayName("authenticate() : 성공")
            fun authenticate_성공() {
                // Given
                val email = "Lion@example.com"
                val password = "12345678"
                val content = AuthenticationRequest(email, password)

                // When
                val resultActions = mock.perform(
                    MockMvcRequestBuilders.post("/api/auth")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(content))
                )

                // Then
                resultActions
                    .andExpect(MockMvcResultMatchers.status().isOk)
                    .andExpect(MockMvcResultMatchers.jsonPath("\$.email").value(email))
                    .andExpect(MockMvcResultMatchers.jsonPath("\$.nickname").value("사자"))
                    .andExpect(MockMvcResultMatchers.jsonPath("\$.accessToken").isString())
                    .andExpect(MockMvcResultMatchers.cookie().exists("refreshToken"))
                    .andExpect(MockMvcResultMatchers.cookie().path("refreshToken", "/"))
                    .andExpect(MockMvcResultMatchers.cookie().httpOnly("refreshToken", true))
                    .andExpect(MockMvcResultMatchers.cookie().secure("refreshToken", true))
                    .andDo(MockMvcResultHandlers.print())
            }
        }

        @Nested
        @DisplayName("실패 케이스")
        inner class FailCase {

            @Test
            @DisplayName("authenticate() : 일치하는 사용자 정보가 없을 경우")
            fun authenticate_이메일이_없는_경우() {
                // Given
                val email = "Empty@example.com"
                val password = "87654321"
                val content = AuthenticationRequest(email, password)

                // When
                val resultActions = mock.perform(
                    MockMvcRequestBuilders.post("/api/auth")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(content))
                )

                // Then
                resultActions
                    .andExpect(MockMvcResultMatchers.status().isBadRequest)
                    .andExpect(MockMvcResultMatchers.jsonPath("\$.status").value(400))
                    .andExpect(MockMvcResultMatchers.jsonPath("\$.message").value("이메일 또는 비밀번호가 일치하지 않습니다."))
                    .andDo(MockMvcResultHandlers.print())
            }
        }
    }

    @Nested
    @DisplayName("로그아웃")
    inner class Logout {

        @Nested
        @DisplayName("성공 케이스")
        inner class SuccessCase {

            @Test
            @DisplayName("logout() : 성공")
            fun logout() {
                // Given
                val authentication = authenticationService.authentication(
                    email = "Lion@example.com",
                    password = "12345678"
                )

                // When
                val resultActions = mock.perform(
                    MockMvcRequestBuilders.post("/api/auth/logout")
                        .header("Authorization", "Bearer ${authentication.accessToken}")
                        .cookie(authentication.refreshTokenCookie)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                )

                // Then
                resultActions
                    .andExpect(MockMvcResultMatchers.status().isNoContent())
                    .andExpect(MockMvcResultMatchers.cookie().maxAge("refreshToken", 0))
                    .andDo(MockMvcResultHandlers.print())

                val redisToken = refreshTokenRepository.findByIdOrNull("Lion@example.com")
                assertThat(redisToken).isNull()
            }
        }

        @Nested
        @DisplayName("실패 케이스")
        inner class FailCase {

            @Test
            @DisplayName("logout() : 미로그인")
            fun logout_미로그인() {
                // Given

                // When
                val resultActions = mock.perform(
                    MockMvcRequestBuilders.post("/api/auth/logout")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                )

                // Then
                resultActions
                    .andExpect(MockMvcResultMatchers.status().isUnauthorized)
                    .andExpect(MockMvcResultMatchers.jsonPath("\$.code").value(HttpStatus.UNAUTHORIZED.name))
                    .andExpect(MockMvcResultMatchers.jsonPath("\$.status").value(HttpStatus.UNAUTHORIZED.value()))
                    .andDo(MockMvcResultHandlers.print())
            }
        }
    }
}
