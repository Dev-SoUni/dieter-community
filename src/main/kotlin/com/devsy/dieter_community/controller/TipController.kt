package com.devsy.dieter_community.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/tips")
class TipController {

    @GetMapping("/hello")
    fun hello(): ResponseEntity<String> {

        return ResponseEntity(HttpStatus.OK)
    }
}