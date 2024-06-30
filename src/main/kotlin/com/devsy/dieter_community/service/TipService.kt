package com.devsy.dieter_community.service

import com.devsy.dieter_community.domain.Tip
import com.devsy.dieter_community.repository.TipRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class TipService (
    val tipRepository: TipRepository
) {

    fun findAll(): List<Tip> {
        return tipRepository.findAll()
    }

    fun save(
        title: String,
        content: String,
    ): Tip {

        val tip = Tip(
            title = title,
            content = content,
            createdAt = LocalDateTime.now()
        )

        return tipRepository.save(tip)
    }

}