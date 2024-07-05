package com.devsy.dieter_community.controller

import com.devsy.dieter_community.dto.AuthenticationRequest
import com.devsy.dieter_community.dto.AuthenticationResponse
import com.devsy.dieter_community.exception.CustomException
import com.devsy.dieter_community.service.AuthenticationService
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authenticationService: AuthenticationService,
) {

    @PostMapping
    fun authenticate(
        @RequestBody requestBody: AuthenticationRequest,
    ): AuthenticationResponse =
        authenticationService.authentication(
            requestBody.email,
            requestBody.password,
        )

}