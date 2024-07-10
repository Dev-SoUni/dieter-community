package com.devsy.dieter_community.controller

import com.devsy.dieter_community.domain.Member
import com.devsy.dieter_community.dto.MemberPostRequest
import com.devsy.dieter_community.repository.MemberRepository
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DisplayName
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.transaction.annotation.Transactional
import kotlin.test.Test

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
class MemberControllerTest {

    @Autowired
    private lateinit var mock: MockMvc

    @Autowired
    private lateinit var mapper: ObjectMapper

    @Test
    @DisplayName("회원가입 : 필수값이 비었을 경우")
    fun 회원가입_필수값이_비었을_경우() {
        // Given
        val requestBody = MemberPostRequest(
            email = "",
            nickname = "",
            password = "",
        )

        // When
        val resultActions = mock.perform(
            MockMvcRequestBuilders
                .post("/api/members")
                .content(mapper.writeValueAsString(requestBody))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )

        // Then
        resultActions
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(MockMvcResultMatchers.jsonPath("\$.status").value(HttpStatus.BAD_REQUEST.value()))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.message").value("요청 데이터가 올바르지 않습니다."))
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    @DisplayName("회원가입 : 중복된 이메일")
    fun 회원가입_중복된_이메일() {
        // Given
        val requestBody = MemberPostRequest(
            email = member.email,
            nickname = "북삼이",
            password = "1234",
        )

        // When
        val resultActions = mock.perform(
            MockMvcRequestBuilders
                .post("/api/members")
                .content(mapper.writeValueAsString(requestBody))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )

        // Then
        resultActions
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(MockMvcResultMatchers.jsonPath("\$.status").value(HttpStatus.BAD_REQUEST.value()))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.message").value("해당 이메일은 사용 중입니다."))
            .andDo(MockMvcResultHandlers.print())

    }

    @Test
    @DisplayName("회원가입")
    fun 회원가입(){
        // Given
        val requestBody = MemberPostRequest(
            email = "lion@example.com",
            nickname = "사자",
            password = "1234",
        )

        // When
        val resultActions = mock.perform(
            MockMvcRequestBuilders
                .post("/api/members")
                .content(mapper.writeValueAsString(requestBody))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )

        // Then
        resultActions
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("\$.email").value(requestBody.email))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.nickname").value(requestBody.nickname))
            .andDo(MockMvcResultHandlers.print())
    }

    companion object {
        lateinit var member: Member

        @BeforeAll
        @JvmStatic
        fun beforeAll(
            @Autowired memberRepository: MemberRepository,
        ) {
            member = Member(email = "turtle@example.com", nickname = "거북이", password = "1234")
            memberRepository.save(member)
        }
    }
}