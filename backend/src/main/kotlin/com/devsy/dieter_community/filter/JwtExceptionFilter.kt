package com.devsy.dieter_community.filter

import com.devsy.dieter_community.dto.ErrorResponse
import com.devsy.dieter_community.exception.ErrorCode
import com.fasterxml.jackson.databind.ObjectMapper
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.JwtException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtExceptionFilter : OncePerRequestFilter() {

    private val mapper = ObjectMapper()

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            filterChain.doFilter(request, response)
        } catch (ex: JwtException) {
            when (ex) {
                is ExpiredJwtException ->
                    setErrorResponse(response, ErrorCode.TOKEN_EXPIRED)
                else ->
                    setErrorResponse(response, ErrorCode.TOKEN_ERROR)
            }
        }
    }

    private fun setErrorResponse(
        response: HttpServletResponse,
        code: ErrorCode,
    ) {
        response.status = code.status.value()
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.characterEncoding = "UTF-8"
        response.writer.write(mapper.writeValueAsString(
            ErrorResponse(
                status = code.status.value(),
                message = code.message
            )
        ))
    }
}