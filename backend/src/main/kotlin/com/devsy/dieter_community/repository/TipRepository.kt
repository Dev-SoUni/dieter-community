package com.devsy.dieter_community.repository

import com.devsy.dieter_community.domain.Tip
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TipRepository: JpaRepository<Tip, String> {

    fun findByTitleContaining(
        pageable: Pageable,
        title: String
    ): Page<Tip>
}