package com.devsy.dieter_community.domain

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "tip")
@EntityListeners(AuditingEntityListener::class)
class Tip(
    title: String,
    content: String,
) {

    @Id
    @Column(name = "id")
    var id: String? = null

    @Column(name = "title")
    var title: String = title

    @Column(name = "content")
    var content: String = content

    @Column(name = "writer_id")
    val writerId: Long? = null

    @Column(name = "updated_at")
    @LastModifiedDate
    lateinit var updatedAt: LocalDateTime

    @Column(name = "created_at")
    @CreatedDate
    lateinit var createdAt: LocalDateTime

    @PrePersist
    fun prePersist() {
        this.id = UUID.randomUUID().toString()
    }

}