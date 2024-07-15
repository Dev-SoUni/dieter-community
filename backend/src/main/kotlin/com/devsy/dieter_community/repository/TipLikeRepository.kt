package com.devsy.dieter_community.repository

import com.devsy.dieter_community.domain.Member
import com.devsy.dieter_community.domain.TipLike
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TipLikeRepository: JpaRepository<TipLike, String> {

    fun findByTipIdAndMember(tipId: String, member: Member): TipLike?
}