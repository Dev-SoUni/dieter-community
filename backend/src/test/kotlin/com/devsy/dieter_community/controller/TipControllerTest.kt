package com.devsy.dieter_community.controller

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class TipControllerTest {

    @Autowired
    private lateinit var mock: MockMvc

    @Test
    @DisplayName("꿀팁 게시물 조회 : 없는 ID로 조회 (실패)")
    fun 꿀팁_게시물_없는_ID로_조회() {
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
}