package com.devsy.dieter_community.service

import com.devsy.dieter_community.domain.Tip
import com.devsy.dieter_community.repository.TipRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class TipService (
    val tipRepository: TipRepository
) {

    fun findByTitle(
        pageable: Pageable,
        title: String
    ): Page<Tip> {
        return tipRepository.findByTitleContaining(pageable, title)
    }

    fun findById(id: String): Tip? {
        return tipRepository.findByIdOrNull(id)
    }

    fun patch(
        id: String,
        title: String?,
        content: String?,
    ): Tip? {
        val tip = tipRepository.findByIdOrNull(id) ?: return null

        if (title != null) tip.title = title
        if (content != null) tip.content = content

        return tipRepository.save(tip)
    }

    fun post(tip: Tip): Tip? =
        runCatching { tipRepository.save(tip) }.getOrNull()

    fun delete(id: String): Boolean {
        val found = tipRepository.findByIdOrNull(id)

        return found?.let {
            tipRepository.delete(found)
            true
        } ?: false
    }
}