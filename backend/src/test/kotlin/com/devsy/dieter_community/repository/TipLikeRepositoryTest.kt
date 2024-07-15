package com.devsy.dieter_community.repository

import com.devsy.dieter_community.config.JpaConfig
import com.devsy.dieter_community.domain.Member
import com.devsy.dieter_community.domain.Tip
import com.devsy.dieter_community.domain.TipLike
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DisplayName
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import kotlin.test.Test

@DataJpaTest
@Import(JpaConfig::class)
class TipLikeRepositoryTest {

    @Autowired
    private lateinit var memberRepository: MemberRepository

    @Autowired
    private lateinit var tipRepository: TipRepository

    @Autowired
    private lateinit var tipLikeRepository: TipLikeRepository

    @Test
    @DisplayName("findByTipAndMember() : 성공")
    fun findByTipIdAndMember() {
        // Given
        val writer = Member(email = "Turtle@example.com", nickname = "거북이", password = "")
        memberRepository.save(writer)

        val tip = Tip(title = "꿀팁 제목", writer = writer, content = "꿀팁 내용")
        tipRepository.save(tip)

        val tipLike = TipLike(tip = tip, member = loginMember)
        tipLikeRepository.save(tipLike)

        // When
        val found = tipLikeRepository.findByTipAndMember(tip = tip, member = loginMember)

        // Then
        assertThat(found).isNotNull()
    }

    companion object {
        lateinit var loginMember: Member

        @BeforeAll
        @JvmStatic
        fun beforeAll(@Autowired memberRepository: MemberRepository) {
            loginMember = Member(email = "Lion@example.com", nickname = "사자", password = "")
            memberRepository.save(loginMember)
        }
    }
}