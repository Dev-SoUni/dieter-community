package com.devsy.dieter_community.filter

import com.devsy.dieter_community.dto.ErrorResponse
import com.fasterxml.jackson.databind.ObjectMapper
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.JwtException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtExceptionFilter : OncePerRequestFilter() {

    private val log = LoggerFactory.getLogger(this.javaClass)
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
                is ExpiredJwtException -> {
                    response.status = HttpStatus.UNAUTHORIZED.value()
                    response.contentType = MediaType.APPLICATION_JSON_VALUE
                    response.characterEncoding = "UTF-8"
                    response.writer.write(mapper.writeValueAsString(
                        ErrorResponse(
                            status = HttpStatus.UNAUTHORIZED.value(),
                            message = "토큰이 만료되었습니다."
                        )
                    ))
                }
                else -> {
                    response.status = HttpStatus.UNAUTHORIZED.value()
                    response.contentType = MediaType.APPLICATION_JSON_VALUE
                    response.characterEncoding = "UTF-8"
                    response.writer.write(mapper.writeValueAsString(
                        ErrorResponse(
                            status = HttpStatus.UNAUTHORIZED.value(),
                            message = "토큰을 처리하는 중 문제가 발생했습니다."
                        )
                    ))
                }
            }
        }
    }

    private fun setErrorResponse(response: HttpServletResponse) {
    }
}