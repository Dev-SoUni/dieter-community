package com.devsy.dieter_community.domain

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@Entity
@Table(name = "member")
@EntityListeners(AuditingEntityListener::class)
class Member(
    email: String,
    nickname: String,
    password: String,
) {

    @Id
    @Column(name = "id")
    var id: String? = null

    @Column(name = "email")
    var email: String = email

    @Column(name = "nickname")
    val nickname: String = nickname

    @Column(name = "password")
    val password: String = password

    @Column(name = "updated_at")
    @LastModifiedDate
    lateinit var updatedAt: LocalDateTime

    @Column(name = "created_at")
    @CreatedDate
    lateinit var createdAt: LocalDateTime
}
