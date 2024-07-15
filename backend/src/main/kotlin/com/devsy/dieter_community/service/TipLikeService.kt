package com.devsy.dieter_community.service

import com.devsy.dieter_community.domain.Member
import com.devsy.dieter_community.domain.TipLike
import com.devsy.dieter_community.repository.TipLikeRepository
import com.devsy.dieter_community.repository.TipRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class TipLikeService(
    val tipRepository: TipRepository,
    val tipLikeRepository: TipLikeRepository,
) {

    fun post(tipId: String, member: Member): TipLike? {
        val tip = tipRepository.findByIdOrNull(tipId) ?: return null
        val tipLike = TipLike(tip = tip, member = member)

        return runCatching {
            tipLikeRepository.save(tipLike)
        }.getOrNull()
    }

    fun delete(id: String): Boolean {
        val found = tipLikeRepository.findByIdOrNull(id)

        return found?.let {
            tipLikeRepository.delete(found)
            true
        } ?: false
    }
}