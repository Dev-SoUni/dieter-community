package com.devsy.dieter_community.domain

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "member")
@EntityListeners(AuditingEntityListener::class)
class Member(
    email: String,
    nickname: String,
    password: String,
) : UserDetails {

    @Id
    @Column(name = "id")
    var id: String? = null

    @Column(name = "email")
    var email: String = email

    @Column(name = "nickname")
    val nickname: String = nickname

    @Column(name = "password")
    private val password: String = password

    @Column(name = "updated_at")
    @LastModifiedDate
    lateinit var updatedAt: LocalDateTime

    @Column(name = "created_at")
    @CreatedDate
    lateinit var createdAt: LocalDateTime

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        val authority = SimpleGrantedAuthority("USER")

        return mutableListOf(authority)
    }

    override fun getUsername(): String {
        return this.email
    }

    override fun getPassword(): String {
        return this.password
    }

    @PrePersist
    fun prePersist() {
        this.id = UUID.randomUUID().toString()
    }
}


