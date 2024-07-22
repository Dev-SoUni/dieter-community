package com.devsy.dieter_community.controller

import com.devsy.dieter_community.controller.TipControllerTest.Companion.member
import com.devsy.dieter_community.controller.TipControllerTest.Companion.tips
import com.devsy.dieter_community.domain.Member
import com.devsy.dieter_community.domain.Tip
import com.devsy.dieter_community.domain.TipLike
import com.devsy.dieter_community.dto.TipLikePostRequest
import com.devsy.dieter_community.providers.createAccessToken
import com.devsy.dieter_community.providers.generateMember
import com.devsy.dieter_community.repository.MemberRepository
import com.devsy.dieter_community.repository.TipLikeRepository
import com.devsy.dieter_community.repository.TipRepository
import com.devsy.dieter_community.service.TokenService
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.transaction.Transactional
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import kotlin.test.fail

// TODO: 안되는 케이스 있슴
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
class TipLikeControllerTest {

    @Autowired
    private lateinit var mock: MockMvc

    @Autowired
    private lateinit var mapper: ObjectMapper

    @Autowired
    private lateinit var tokenService: TokenService

    @Nested
    @DisplayName("꿀팁 좋아요 조회")
    inner class 꿀팁_좋아요_조회 {

        @Nested
        @DisplayName("성공 케이스")
        inner class SuccessCase {

            @Test
            @DisplayName("꿀팁 좋아요 조회 : 좋아요한 상태")
            fun 꿀팁_좋아요_조회_좋아요한_상태() {
                // Given
                val accessToken = createAccessToken(member, tokenService)
                val tip = tips[0] ?: fail("꿀팁을 찾을 수 없음")

                // When
                val resultActions = mock.perform(
                    MockMvcRequestBuilders
                        .get("/api/tip-likes/auth?tipId=${tip.id}")
                        .header("Authorization", "Bearer $accessToken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )

                // Then
                resultActions
                    .andExpect(MockMvcResultMatchers.status().isOk)
                    .andExpect(MockMvcResultMatchers.jsonPath("\$.tip").exists())
                    .andExpect(MockMvcResultMatchers.jsonPath("\$.tip.id").value(tip.id))
                    .andDo(MockMvcResultHandlers.print())
            }

        }

        @Nested
        @DisplayName("실패 케이스")
        inner class FailCase {

            // TODO: 수정 필요 204나와야 하는데 200 나옴
            @Test
            @DisplayName("꿀팁 좋아요 조회 : 좋아요 안한 상태")
            fun 꿀팁_좋아요_조회_좋아요_안한_상태() {
                // Given
                val accessToken = createAccessToken(generateMember(email = "", nickname = ""), tokenService)
                val tipId = tips[0].id ?: fail("꿀팁을 찾을 수 없음")

                // When
                val resultActions = mock.perform(
                    MockMvcRequestBuilders
                        .get("/api/tip-likes/auth?tipId=$tipId")
                        .header("Authorization", "Bearer $accessToken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )

                // Then
                resultActions
                    .andExpect(MockMvcResultMatchers.status().isNoContent)
                    .andDo(MockMvcResultHandlers.print())
            }
        }
    }

    @Nested
    @DisplayName("꿀팁 좋아요 등록")
    inner class 꿀팁_좋아요_등록 {

        @Nested
        @DisplayName("성공 케이스")
        inner class SuccessCase {

            @Test
            @DisplayName("꿀팁 좋아요 등록")
            fun 꿀팁_좋아요_등록() {
                // Given
                val member_two = generateMember(email = "", nickname = "")
                val accessToken = createAccessToken(member_two, tokenService)
                val id = tips[0].id ?: fail("꿀팁을 찾을 수 없음")
                val requestBody = TipLikePostRequest(tipId = id)

                // When
                val resultActions = mock.perform(
                    MockMvcRequestBuilders
                        .post("/api/tip-likes/")
                        .header("authorization", "Bearer $accessToken")
                        .content(mapper.writeValueAsString(requestBody))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )

                // Then
                resultActions
                    .andExpect(MockMvcResultMatchers.status().isOk)
                    .andDo(MockMvcResultHandlers.print())
            }
        }

        @Nested
        @DisplayName("실패 케이스")
        inner class FailCase {

            @Test
            @DisplayName("꿀팁 좋아요 등록 : 이미 좋아요 등록된 경우")
            fun 꿀팁_좋아요_등록_이미_좋아요_등록된_경우() {
                // Given
                val accessToken = createAccessToken(member, tokenService)
                val tipId = tips[0].id ?: fail("꿀팁을 찾을 수 없음")
                val requestBody = TipLikePostRequest(tipId = tipId)


                // When
                val resultActions = mock.perform(
                    MockMvcRequestBuilders
                        .post("/api/tip-likes/")
                        .header("authorization", "Bearer $accessToken")
                        .content(mapper.writeValueAsString(requestBody))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )

                // Then
                resultActions
                    .andExpect(MockMvcResultMatchers.status().isBadRequest)
                    .andDo(MockMvcResultHandlers.print())
            }
        }

    }

    companion object {

        @BeforeAll
        @JvmStatic
        fun beforeAll(
            @Autowired memberRepository: MemberRepository,
            @Autowired tipRepository: TipRepository,
            @Autowired tipLikeRepository: TipLikeRepository,
        ) {
            member = Member(email = "Lion@example.com", nickname = "사자", password = "1234")
            memberRepository.save(member)

            tips = listOf(
                Tip(title = "꿀팁01", writer = member, content = "내용01"),
                Tip(title = "꿀팁02", writer = member, content = "내용02"),
                Tip(title = "꿀팁03", writer = member, content = "내용03"),
                Tip(title = "꿀팁04", writer = member, content = "내용04"),
                Tip(title = "꿀팁05", writer = member, content = "내용05"),
                Tip(title = "꿀팁06", writer = member, content = "내용06"),
                Tip(title = "꿀팁07", writer = member, content = "내용07"),
                Tip(title = "꿀팁08", writer = member, content = "내용08"),
                Tip(title = "꿀팁09", writer = member, content = "내용09"),
                Tip(title = "꿀팁10", writer = member, content = "내용10"),
            )
            tipRepository.saveAll(tips)

            val tipLike = TipLike(
                tip = tips[0],
                member = member,
            )
            tipLikeRepository.save(tipLike)
        }
    }

}