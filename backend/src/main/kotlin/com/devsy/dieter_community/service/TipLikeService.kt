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

        // 해당 게시물에 이미 좋아요가 되어있다면 불가능
        val found = tipLikeRepository.findByTipAndMember(tip = tip, member = member)
        if (found != null) return null

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