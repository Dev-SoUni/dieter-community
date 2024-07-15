package com.devsy.dieter_community.domain

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "tip_like")
@EntityListeners(AuditingEntityListener::class)
class TipLike(
    tip: Tip,
    member: Member,
) {

    @Id
    @Column(name = "id")
    var id: String? = null

    @JoinColumn(name = "tip_id")
    @ManyToOne(fetch = FetchType.LAZY)
    val tip: Tip = tip

    @JoinColumn(name = "member_id")
    @ManyToOne(fetch = FetchType.LAZY)
    val member: Member = member

    @Column(name = "created_at")
    @CreatedDate
    lateinit var createdAt: LocalDateTime

    @PrePersist
    fun prePersist() {
        this.id = UUID.randomUUID().toString()
    }
}