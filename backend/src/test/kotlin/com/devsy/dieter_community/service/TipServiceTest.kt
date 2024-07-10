package com.devsy.dieter_community.service

import com.devsy.dieter_community.domain.Member
import com.devsy.dieter_community.domain.Tip
import com.devsy.dieter_community.repository.MemberRepository
import com.devsy.dieter_community.repository.TipRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DisplayName
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import kotlin.test.Test
import kotlin.test.fail

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
class TipServiceTest {

    @Autowired
    private lateinit var tipService: TipService

    @Test
    @DisplayName("꿀팁 삭제 : 성공")
    fun 삭제() {
        // Given
        val id = tips[0].id ?: fail("꿀팁을 찾을 수 없음")

        // When
        val success = tipService.delete(id)

        // Then
        assertThat(success).isTrue()
    }

    @Test
    @DisplayName("꿀팁 삭제 : 존재하지 않는 ID")
    fun 삭제_존재하지_않는_ID() {
        // Given
        val id = "wrong_post_id"

        // When
        val success = tipService.delete(id)

        // Then
        assertThat(success).isFalse()
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