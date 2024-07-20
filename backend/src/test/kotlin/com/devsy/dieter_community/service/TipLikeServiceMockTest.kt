package com.devsy.dieter_community.service

import com.devsy.dieter_community.domain.Tip
import com.devsy.dieter_community.domain.TipLike
import com.devsy.dieter_community.exception.CustomException
import com.devsy.dieter_community.providers.generateMember
import com.devsy.dieter_community.repository.TipLikeRepository
import com.devsy.dieter_community.repository.TipRepository
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
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
    @DisplayName("꿀팁 좋아요 조회")
    inner class GetByTipAndMember {

        @Nested
        @DisplayName("성공 케이스")
        inner class SuccessCase {

            @Test
            @DisplayName("getByTipAndMember() : 성공")
            fun getByTipAndMember() {
                // Given
                val tipId = "새로운_ID"
                val member = generateMember(email = "Lion@example.com", nickname = "사자")
                val tip = Tip(title = "", writer = generateMember(email = "", nickname = ""), content = "")

                // When
                Mockito.`when`(
                    tipRepository.findById(tipId)
                ).thenReturn(
                    Optional.of(tip)
                )
                Mockito.`when`(
                    tipLikeRepository.findByTipAndMember(tip, member)
                ).thenReturn(
                    TipLike(tip = tip, member = member)
                )

                val tipLike = tipLikeService.getByTipAndMember(tipId, member)

                // Then
                assertThat(tipLike).isNotNull()
                assertThat(tipLike?.tip).isEqualTo(tip)
                assertThat(tipLike?.member).isEqualTo(member)
            }
        }

        @Nested
        @DisplayName("실패 케이스")
        inner class FailCase {

            @Test
            @DisplayName("getByTipAndMember() : 해당 꿀팁 게시물이 없는 경우")
            fun getByTipAndMember_해당_꿀팁_게시물이_없는_경우() {
                // Given
                val tipId = "잘못된_ID"
                val member = generateMember(email = "Lion@example.com", nickname = "사자")

                // When, Then
                Mockito.`when`(
                    tipRepository.findById(tipId)
                ).thenReturn(Optional.empty())

                assertThatThrownBy { tipLikeService.getByTipAndMember(tipId, member) }
                    .isInstanceOf(CustomException::class.java)
                    .withFailMessage("꿀팁 게시물에 대한 정보를 찾을 수 없습니다.")
            }

        }
    }


    @Nested
    @DisplayName("꿀팁 좋아요 등록")
    inner class Post {

        @Nested
        @DisplayName("성공 케이스")
        inner class SuccessCase {

            @Test
            @DisplayName("post() : 성공")
            fun post() {
                // Given
                val tipId = "새로운_ID"
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
                Mockito.`when`(tipLikeRepository.findByTipAndMember(tip = org.mockito.kotlin.any(), member = org.mockito.kotlin.any()))
                    .thenReturn(null)
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
        inner class FailCase {

            @Test
            @DisplayName("post() : 해당 꿀팁 게시물이 없는 경우")
            fun post_해당_꿀팁_게시물이_없는_경우() {
                // Given
                val tipId = "잘못된_ID"
                val member = generateMember(email = "Lion@example.com", nickname = "사자")

                // When
                Mockito.`when`(tipRepository.findById(tipId)).thenReturn(Optional.empty())

                val posted = tipLikeService.post(tipId = tipId, member = member)

                // Then
                assertThat(posted).isNull()
            }

            @Test
            @DisplayName("post() : 이미 좋아요가 된 꿀팁일 경우")
            fun post_이미_좋아요가_된_꿀팁일_경우() {
                // Given
                val tip = Tip(title = "", writer = generateMember(email = "", nickname = ""), content = "")
                val tipId = "tip-id"
                val member = generateMember(email = "Lion@example.com", nickname = "사자")

                // When
                Mockito.`when`(tipRepository.findById(tipId))
                    .thenReturn(Optional.of(tip))
                Mockito.`when`(tipLikeRepository.findByTipAndMember(tip = org.mockito.kotlin.any(), member = org.mockito.kotlin.any()))
                    .thenReturn(TipLike(tip = tip, member = member))

                val posted = tipLikeService.post(tipId = tipId, member = member)

                // Then
                assertThat(posted).isNull()
            }
        }
    }

    @Nested
    @DisplayName("꿀팁 좋아요 삭제")
    inner class Delete {

        @Nested
        @DisplayName("성공 케이스")
        inner class SuccessCase {

            @Test
            @DisplayName("deleteByTipAndMember() : 성공")
            fun deleteByTipAndMember_성공() {
                // Given
                val tipId = "올바른_ID"
                val tip = Tip(title = "", writer = generateMember(email = "", nickname = ""), content = "")
                val member = generateMember(email = "Lion@example.com", nickname = "사자")
                val tipLike = TipLike(tip = tip, member = member)

                // When
                Mockito.`when`(
                    tipRepository.findById(tipId)
                ).thenReturn(
                    Optional.of(tip)
                )
                Mockito.`when`(
                    tipLikeRepository.findByTipAndMember(tip, member)
                ).thenReturn(tipLike)
                Mockito.doNothing().`when`(tipLikeRepository).delete(tipLike)

                val success = tipLikeService.deleteByTipAndMember(tipId, member)

                // Then
                assertThat(success).isTrue()
            }
        }

        @Nested
        @DisplayName("실패 케이스")
        inner class FailCase {

            @Test
            @DisplayName("deleteByTipAndMember() : 해당 꿀팁 게시물이 없는 경우")
            fun deleteByTipAndMember_좋아요가_되어_있지_않은_경우() {
                // Given
                val tipId = "잘못된_ID"
                val member = generateMember(email = "Lion@example.com", nickname = "사자")

                // When, Then
                Mockito.`when`(
                    tipRepository.findById(tipId)
                ).thenReturn(Optional.empty())

                assertThatThrownBy { tipLikeService.deleteByTipAndMember(tipId, member) }
                    .isInstanceOf(CustomException::class.java)
                    .withFailMessage("꿀팁 게시물에 대한 정보를 찾을 수 없습니다.")

            }

            @Test
            @DisplayName("deleteByTipAndMember() : 해당 꿀팁 게시물 좋아요가 없는 경우")
            fun deleteByTipAndMember_해당_꿀팁_게시물_좋아요가_없는_경우() {
                // Given
                val tipId = "올바른_ID"
                val member = generateMember(email = "Lion@example.com", nickname = "사자")
                val tip = Tip(title = "", writer = generateMember(email = "", nickname = ""), content = "")

                // When, Then
                Mockito.`when`(
                    tipRepository.findById(tipId)
                ).thenReturn(
                    Optional.of(tip)
                )
                Mockito.`when`(
                    tipLikeRepository.findByTipAndMember(tip, member)
                ).thenReturn(null)
                assertThatThrownBy { tipLikeService.deleteByTipAndMember(tipId, member) }
                    .isInstanceOf(CustomException::class.java)
                    .withFailMessage("꿀팁 게시물 좋아요에 대한 정보를 찾을 수 없습니다.")
            }

        }
    }
}