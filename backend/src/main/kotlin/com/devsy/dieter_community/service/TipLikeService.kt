package com.devsy.dieter_community.service

import com.devsy.dieter_community.domain.Member
import com.devsy.dieter_community.domain.TipLike
import com.devsy.dieter_community.exception.CustomException
import com.devsy.dieter_community.repository.TipLikeRepository
import com.devsy.dieter_community.repository.TipRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class TipLikeService(
    val tipRepository: TipRepository,
    val tipLikeRepository: TipLikeRepository,
) {

    fun getByTipAndMember(
        tipId: String,
        member: Member,
    ): TipLike? {
        val tip = tipRepository.findByIdOrNull(tipId) ?: throw CustomException(HttpStatus.BAD_REQUEST, "꿀팁 게시물에 대한 정보를 찾을 수 없습니다.")

        return tipLikeRepository.findByTipAndMember(tip, member)
    }

    fun post(
        tipId: String,
        member: Member,
    ): TipLike? {
        val tip = tipRepository.findByIdOrNull(tipId) ?: return null
        val tipLike = TipLike(tip = tip, member = member)

        // 해당 게시물에 이미 좋아요가 되어있다면 불가능
        val found = tipLikeRepository.findByTipAndMember(tip = tip, member = member)
        if (found != null) return null

        return runCatching {
            tipLikeRepository.save(tipLike)
        }.getOrNull()
    }

    fun deleteByTipAndMember(
        tipId: String,
        member: Member,
    ): Boolean {
        val tip = tipRepository.findByIdOrNull(tipId) ?: throw CustomException(HttpStatus.BAD_REQUEST, "꿀팁 게시물에 대한 정보를 찾을 수 없습니다.")
        val tipLike = tipLikeRepository.findByTipAndMember(tip, member) ?: throw CustomException(HttpStatus.BAD_REQUEST, "꿀팁 게시물 좋아요에 대한 정보를 찾을 수 없습니다.")

        return runCatching {
            tipLikeRepository.delete(tipLike)
            true
        }.getOrElse { false }
    }
}