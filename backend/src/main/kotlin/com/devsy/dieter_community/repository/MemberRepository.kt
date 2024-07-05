package com.devsy.dieter_community.repository

import com.devsy.dieter_community.domain.Member
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MemberRepository: JpaRepository<Member, String>{

    fun findByEmail(email: String): Member?

}