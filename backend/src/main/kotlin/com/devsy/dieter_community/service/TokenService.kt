package com.devsy.dieter_community.service

import io.jsonwebtoken.JwtException
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
            .expiration(Date(expirationTime))
            .signWith(this.key)
            .compact()
    }

    fun isValid(token: String): Boolean {
        val parser = getParser()

        try {
            parser.parseSignedClaims(token)
            return true
        } catch (ex: Exception) {
            return when(ex){
                is JwtException, is IllegalArgumentException -> {
                    false
                }
                else -> {
                    false
                }
            }
        }
    }

    private fun getParser(): JwtParser {
        return Jwts.parser().verifyWith(this.key).build()
    }

}