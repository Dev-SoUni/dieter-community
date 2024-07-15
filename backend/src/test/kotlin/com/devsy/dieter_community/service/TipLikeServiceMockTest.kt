package com.devsy.dieter_community.service

import com.devsy.dieter_community.domain.Tip
import com.devsy.dieter_community.domain.TipLike
import com.devsy.dieter_community.providers.generateMember
import com.devsy.dieter_community.repository.TipLikeRepository
import com.devsy.dieter_community.repository.TipRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.AdditionalAnswers
import org.mockito.ArgumentMatchers.any
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import java.util.*
import kotlin.test.Test

@ExtendWith(MockitoExtension::class)
class TipLikeServiceMockTest {

    @InjectMocks
    private lateinit var tipLikeService: TipLikeService

    @Mock
    private lateinit var tipRepository: TipRepository

    @Mock
    private lateinit var tipLikeRepository: TipLikeRepository

    @Nested
    @DisplayName("좋아요 등록")
    inner class Post {

        @Nested
        @DisplayName("성공 케이스")
        inner class SuccessCase {

            @Test
            @DisplayName("post() : 성공")
            fun post() {
                // Given
                val tipId = "new_tip_id"
                val member = generateMember(email = "Lion@example.com", nickname = "사자")

                // When
                Mockito.`when`(tipRepository.findById(tipId))
                    .thenReturn(
                        Optional.of(
                            Tip(
                                title = "꿀팁 제목",
                                writer = generateMember(email = "Turtle@example.com", nickname = "거북이"),
                                content = "꿀팁 내용",
                            ).apply {
                                this.id = tipId
                            }
                        )
                    )
                Mockito.`when`(tipLikeRepository.save(any(TipLike::class.java)))
                    .then(AdditionalAnswers.returnsFirstArg<TipLike>())

                val posted = tipLikeService.post(tipId = tipId, member = member)

                // Then
                assertThat(posted).isNotNull()
                assertThat(posted?.tip?.id).isEqualTo(tipId)
                assertThat(posted?.member).isEqualTo(member)
            }
        }

        @Nested
        @DisplayName("실패 케이스")
        inner class FailCase {}
    }
}