package com.devsy.dieter_community.service

import com.devsy.dieter_community.domain.Member
import com.devsy.dieter_community.domain.Tip
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

// Kotlin 테스트 환경에서 findByIdOrNull() Mocking 시 에러 발생 우회 방법
// 참고 : https://backendbrew.com/docs/error-bug/spring/kotlin_cannot_be_Returned_by_findById

@ExtendWith(MockitoExtension::class)
class TipServiceMockTest {

    @InjectMocks
    private lateinit var tipService: TipService

    @Mock
    private lateinit var tipRepository: TipRepository

    @Nested
    @DisplayName("게시물 조회")
    inner class Select {

        @Nested
        @DisplayName("성공 케이스")
        inner class SuccessCase() {

            @Test
            @DisplayName("findById() : 데이터 조회 성공")
            fun findById() {
                // Given
                val id = "uuid"

                // When
                findByIdSuccessMocking(id)
                val tip = tipService.findById(id)

                // Then
                assertThat(tip).isNotNull()
                assertThat(tip?.id).isEqualTo(id)
            }
        }

        @Nested
        @DisplayName("실패 케이스")
        inner class FailCase() {

            @Test
            @DisplayName("findById() : 데이터 조회 실패")
            fun findById() {
                // Given
                val id = "uuid"

                // Mocking
                Mockito.`when`(
                    tipRepository.findById(id)
                ).thenReturn(Optional.empty())

                // When
                val tip = tipService.findById(id)

                // Then
                assertThat(tip).isNull()
            }
        }
    }

    @Nested
    @DisplayName("게시물 수정")
    inner class Patch {

        @Nested
        @DisplayName("성공 케이스")
        inner class SuccessCase {

            @Test
            @DisplayName("patch() : 성공")
            fun patch() {
                // Given
                val id = "uuid"
                val title = "제목(수정)"
                val content = null

                // When
                findByIdSuccessMocking(id)
                saveSuccessMocking()

                val tip = tipService.patch(id, title, content)

                // Then
                assertThat(tip).isNotNull()
                assertThat(tip?.title).isEqualTo(title)
                assertThat(tip?.content).isEqualTo("내용")
            }
        }

        @Nested
        @DisplayName("실패 케이스")
        inner class FailCase {

            @Test
            @DisplayName("patch() : 존재하지 않는 ID일 경우")
            fun patch() {
                // Given
                val id = "uuid"
                val title = "제목(수정)"
                val content = null

                Mockito.`when`(tipRepository.findById(id)).thenReturn(Optional.empty())

                // When
                val patched = tipService.patch(id = id, title = title, content = content)

                // Then
                assertThat(patched).isNull()
            }
        }
    }

    @Nested
    @DisplayName("게시물 등록")
    inner class Post {

        @Nested
        @DisplayName("성공 케이스")
        inner class SuccessCase {

            @Test
            @DisplayName("post() : 성공")
            fun post() {
                // Given
                val tip = generateTip()

                // When
                saveSuccessMocking()

                val posted = tipService.post(tip)

                // Then
                assertThat(posted).isNotNull()
            }
        }

        @Nested
        @DisplayName("실패 케이스")
        inner class FailCase {

            @Test
            @DisplayName("post() : 실패")
            fun post() {
                // Given
                val tip = generateTip()

                // When
                saveThrowMocking()

                val posted = tipService.post(tip)

                // Then
                assertThat(posted).isNull()
            }
        }
    }

    @Nested
    @DisplayName("게시물 삭제")
    inner class Delete {

        @Nested
        @DisplayName("성공 케이스")
        inner class SuccessCase {

            @Test
            @DisplayName("delete() : 성공")
            fun delete() {
                // Given
                val id = "uuid"

                // When
                findByIdSuccessMocking(id)
                Mockito.doNothing().`when`(tipRepository).delete(any(Tip::class.java))

                val success = tipService.delete(id)

                // Then
                assertThat(success).isTrue()
            }
        }

        @Nested
        @DisplayName("실패 케이스")
        inner class FailCase {

            @Test
            @DisplayName("delete() : 실패")
            fun delete() {
                // Given
                val id = "uuid"

                // When
                Mockito.`when`(tipRepository.findById(id)).thenReturn(Optional.empty())

                val success = tipService.delete(id)

                // Then
                assertThat(success).isFalse()

            }
        }
    }

    private fun generateTip() =
        Tip(
            title = "제목",
            writer = Member(email = "Lion@exmaple.com", nickname = "사자", password = ""),
            content = "내용"
        )

    private fun findByIdSuccessMocking(id: String) {
        Mockito.`when`(tipRepository.findById(id))
            .thenReturn(
                Optional.of(
                    generateTip().apply { this.id = id }
                )
            )
    }

    private fun saveSuccessMocking() {
        Mockito.`when`(tipRepository.save(any(Tip::class.java)))
            .then(AdditionalAnswers.returnsFirstArg<Tip>())
    }

    private fun saveThrowMocking() {
        Mockito.`when`(tipRepository.save(any(Tip::class.java)))
            .thenThrow(IllegalArgumentException::class.java)
    }
}