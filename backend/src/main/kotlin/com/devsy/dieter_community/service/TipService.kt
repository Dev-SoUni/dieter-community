package com.devsy.dieter_community.service

import com.devsy.dieter_community.domain.Tip
import com.devsy.dieter_community.repository.TipRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class TipService (
    val tipRepository: TipRepository
) {

    fun findAll(): List<Tip> {
        return tipRepository.findAll()
    }

    fun findById(id: String): Tip? {
        return tipRepository.findByIdOrNull(id)
    }

    fun patch(
        id: String,
        title: String?,
        content: String?,
    ): Tip {
        val tip = tipRepository.findByIdOrNull(id) ?: throw NullPointerException("해당 ${id}의 팁 게시물을 찾을 수 없습니다.")

        if (title != null) tip.title = title
        if (content != null) tip.content = content

        return tipRepository.save(tip)
    }

    fun post(
        title: String,
        content: String,
    ): Tip {

        val tip = Tip(
            title = title,
            content = content,
        )

        return tipRepository.save(tip)
    }

    fun delete(
        id: String
    ) {
        val tip = tipRepository.findByIdOrNull(id) ?: throw NullPointerException("해당 ${id}의 팁 게시물을 찾을 수 없습니다.")

        tipRepository.delete(tip)
    }
}