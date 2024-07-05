package com.devsy.dieter_community.domain

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "tip_like")
@EntityListeners(AuditingEntityListener::class)
class TipLike(
    tipId: String,
    memberId: String,
) {

    @Id
    @Column(name = "id")
    var id: String? = null

    @Column(name = "tip_id")
    val tipId: String = tipId

    @Column(name = "member_id")
    val memberId: String = memberId

    @Column(name = "created_at")
    @CreatedDate
    lateinit var createdAt: LocalDateTime

    @PrePersist
    fun prePersist() {
        this.id = UUID.randomUUID().toString()
    }
}