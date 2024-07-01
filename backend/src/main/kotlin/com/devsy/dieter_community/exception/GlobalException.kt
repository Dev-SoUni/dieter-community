package com.devsy.dieter_community.exception

import com.devsy.dieter_community.dto.ErrorResponseDTO
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@RestControllerAdvice
class GlobalException : ResponseEntityExceptionHandler() {

    @ExceptionHandler(CustomException::class)
    fun handleCustomException(e: CustomException): ResponseEntity<ErrorResponseDTO> {
        return ResponseEntity
            .status(e.status)
            .body(
                ErrorResponseDTO(
                    e.status.value(),
                    e.message,
                )
            )
    }


    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<ErrorResponseDTO> {
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(
                ErrorResponseDTO(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "내부 서버 오류"
                )
            )
    }

}