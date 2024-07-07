package com.devsy.dieter_community.service

import io.jsonwebtoken.Claims
import io.jsonwebtoken.JwtParser
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.util.*

@Suppress("CanBePrimaryConstructorProperty")
@Service
class TokenService(
    @Value("\${jwt.secret}")
    secret: String,

    @Value("\${jwt.expiration_time}")
    expirationTime: Long
) {

    private val key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret))
    private val expirationTime: Long = expirationTime

    fun generate(userDetails: UserDetails): String {
        return Jwts.builder()
            .subject(userDetails.username)
            .issuedAt(Date(System.currentTimeMillis()))
            .expiration(Date(System.currentTimeMillis() + expirationTime))
            .signWith(this.key)
            .compact()
    }

    fun isValid(
        token: String,
        userDetails: UserDetails,
    ): Boolean {
        val email = extractEmail(token)

        return userDetails.username == email && !isExpired(token)
    }

    fun extractEmail(token: String): String? = getAllClaims(token).subject

    fun isExpired(token: String): Boolean = getAllClaims(token).expiration.before(Date(System.currentTimeMillis()))

    private fun getParser(): JwtParser = Jwts.parser().verifyWith(this.key).build()

    private fun getAllClaims(token: String): Claims = getParser().parseSignedClaims(token).payload

}