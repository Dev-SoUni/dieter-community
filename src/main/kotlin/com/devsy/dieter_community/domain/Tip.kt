package com.devsy.dieter_community.domain

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "tip")
class Tip(
    title: String,
    content: String,
    createdAt: LocalDateTime,
) {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @Column(name = "title")
    var title: String = title

    @Column(name = "content")
    var content: String = content

    @Column(name = "writer_id")
    val writerId: Long? = null

    @Column(name = "updated_at")
    var updatedAt: LocalDateTime? = null

    @Column(name = "created_at")
    val createdAt: LocalDateTime = createdAt

}