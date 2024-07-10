package com.devsy.dieter_community.controller

import com.devsy.dieter_community.domain.Member
import com.devsy.dieter_community.domain.Tip
import com.devsy.dieter_community.dto.TipPatchRequest
import com.devsy.dieter_community.dto.TipPostRequest
import com.devsy.dieter_community.repository.MemberRepository
import com.devsy.dieter_community.repository.TipRepository
import com.devsy.dieter_community.service.TokenService
import com.fasterxml.jackson.databind.ObjectMapper
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DisplayName
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
import org.springframework.transaction.annotation.Transactional
import kotlin.test.Test
import kotlin.test.fail

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
class TipControllerTest {

    @Autowired
    private lateinit var mock: MockMvc

    @Autowired
    private lateinit var mapper: ObjectMapper

    @Autowired
    private lateinit var tokenService: TokenService

    @Autowired
    private lateinit var tipRepository: TipRepository

    @Test
    @DisplayName("꿀팁 게시물 전체 조회")
    fun 꿀팁_게시물_전체_조회() {
        // Given

        // When
        val resultActions = mock.perform(
            MockMvcRequestBuilders
                .get("/api/tips")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )

        // Then
        resultActions
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("\$.number").value(0))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.totalPages").value(1))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.totalElements").value(10))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.content").isArray)
            .andExpect(MockMvcResultMatchers.jsonPath("\$.content.length()").value(10))
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    @DisplayName("꿀팁 게시물 ID로 조회 : 존재하는 ID일 경우")
    fun 꿀팁_게시물_존재하는_ID로_조회() {
        // Given
        val tip = tips[0]

        // When
        val resultActions = mock.perform(
            MockMvcRequestBuilders
                .get("/api/tips/${tip.id}")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )

        // Then
        resultActions
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("\$.id").value(tip.id))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.title").value(tip.title))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.content").value(tip.content))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.writer.email").value(tip.writer.email))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.writer.nickname").value(tip.writer.nickname))
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    @DisplayName("꿀팁 게시물 ID로 조회 : 존재하지 않는 ID일 경우")
    fun 꿀팁_게시물_존재하지_않는_ID로_조회() {
        // Given
        val id = "wrong_post_id"

        // When
        val resultActions = mock.perform(
            MockMvcRequestBuilders
                .get("/api/tips/$id")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )

        // Then
        resultActions
            .andExpect(MockMvcResultMatchers.status().isNotFound)
            .andExpect(MockMvcResultMatchers.jsonPath("\$.status").value(404))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.message").value("해당 꿀팁을 찾을 수 없습니다."))
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    @DisplayName("꿀팁 등록 : 로그인하지 않은 상태")
    fun 꿀팁_등록_미로그인_상태() {
        // Given
        val requestBody = TipPostRequest(title = "꿀팁(New)", content = "내용(New)")

        // When
        val resultActions = mock.perform(
            MockMvcRequestBuilders
                .post("/api/tips")
                .content(mapper.writeValueAsString(requestBody))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )

        // Then
        resultActions
            .andExpect(MockMvcResultMatchers.status().isUnauthorized)
            .andExpect(MockMvcResultMatchers.jsonPath("\$.status").value(HttpStatus.UNAUTHORIZED.value()))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.message").value("API 이용에 대한 인증에 실패했습니다."))
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    @DisplayName("꿀팁 등록 : 로그인한 상태")
    fun 꿀팁_등록_로그인_상태() {
        // Given
        val accessToken = tokenService.generate(member)
        val requestBody = TipPostRequest(title = "꿀팁(New)", content = "내용(New)")

        // When
        val resultActions = mock.perform(
            MockMvcRequestBuilders
                .post("/api/tips")
                .header("Authorization", "Bearer $accessToken")
                .content(mapper.writeValueAsString(requestBody))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )

        // Then
        resultActions
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("\$.id").isNotEmpty)
            .andExpect(MockMvcResultMatchers.jsonPath("\$.title").value(requestBody.title))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.content").value(requestBody.content))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.writer.email").value(member.email))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.writer.nickname").value(member.nickname))
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    @DisplayName("꿀팁 수정 : 미로그인 상태")
    fun 꿀팁_수정_미로그인_상태() {
        // Given
        val tip = tips[0]
        val requestBody = TipPatchRequest(title = "꿀팁(Update)", content = "내용(Update)")

        // When
        val resultActions = mock.perform(
            MockMvcRequestBuilders
                .patch("/api/tips/${tip.id}")
                .content(mapper.writeValueAsString(requestBody))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )

        // Then
        resultActions
            .andExpect(MockMvcResultMatchers.status().isUnauthorized)
            .andExpect(MockMvcResultMatchers.jsonPath("\$.status").value(HttpStatus.UNAUTHORIZED.value()))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.message").value("API 이용에 대한 인증에 실패했습니다."))
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    @DisplayName("꿀팁 수정 : 꿀팁 ID가 존재하지 않는 경우")
    fun 꿀팁_수정_존재하지_않는_ID() {
        // Given
        val id = "wrong_post_id"
        val accessToken = tokenService.generate(member)
        val requestBody = TipPatchRequest(title = "꿀팁(Update)", content = "내용(Update)")

        // When
        val resultActions = mock.perform(
            MockMvcRequestBuilders
                .patch("/api/tips/$id")
                .header("Authorization", "Bearer $accessToken")
                .content(mapper.writeValueAsString(requestBody))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )

        // Then
        resultActions
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(MockMvcResultMatchers.jsonPath("\$.status").value(HttpStatus.BAD_REQUEST.value()))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.message").value("꿀팁 수정에 실패했습니다."))
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun 꿀팁_수정() {
        // Given
        val id = tips[0].id ?: fail("꿀팁을 찾을 수 없음")
        val accessToken = tokenService.generate(member)
        val requestBody = TipPatchRequest(title = "꿀팁(Update)", content = "내용(Update)")

        // When
        val resultActions = mock.perform(
            MockMvcRequestBuilders
                .patch("/api/tips/$id")
                .header("Authorization", "Bearer $accessToken")
                .content(mapper.writeValueAsString(requestBody))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )

        // Then
        resultActions
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("\$.title").value(requestBody.title))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.content").value(requestBody.content))
            .andDo(MockMvcResultHandlers.print())

        val found = tipRepository.findByIdOrNull(id)
        assertThat(found).isNotNull()
        assertThat(found?.title).isEqualTo(requestBody.title)
        assertThat(found?.content).isEqualTo(requestBody.content)
    }

    @Test
    @DisplayName("꿀팁 삭제 : 미로그인 상태")
    fun 꿀팁_삭제_미로그인_상태() {
        // Given
        val id = tips[0].id ?: fail("꿀팁을 찾을 수 없음")

        // When
        val resultActions = mock.perform(
            MockMvcRequestBuilders
                .delete("/api/tips/$id")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )

        // Then
        resultActions
            .andExpect(MockMvcResultMatchers.status().isUnauthorized)
            .andExpect(MockMvcResultMatchers.jsonPath("\$.status").value(HttpStatus.UNAUTHORIZED.value()))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.message").value("API 이용에 대한 인증에 실패했습니다."))
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    @DisplayName("꿀팁 삭제 : 꿀팁 ID가 존재하지 않는 경우")
    fun 꿀팁_삭제_존재하지_않는_ID() {
        // Given
        val id = "wrong_post_id"
        val accessToken = tokenService.generate(member)

        // When
        val resultActions = mock.perform(
            MockMvcRequestBuilders
                .delete("/api/tips/$id")
                .header("Authorization", "Bearer $accessToken")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )

        // Then
        resultActions
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(MockMvcResultMatchers.jsonPath("\$.status").value(HttpStatus.BAD_REQUEST.value()))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.message").value("꿀팁 삭제에 실패했습니다."))
            .andDo(MockMvcResultHandlers.print())
    }

    companion object {

        lateinit var member: Member
        lateinit var tips: List<Tip>

        @BeforeAll
        @JvmStatic
        fun beforeAll(
            @Autowired memberRepository: MemberRepository,
            @Autowired tipRepository: TipRepository,
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
        }
    }
}