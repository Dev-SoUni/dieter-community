package com.devsy.dieter_community.controller

import com.devsy.dieter_community.domain.Tip
import com.devsy.dieter_community.dto.PostTipDTO
import com.devsy.dieter_community.service.TipService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/tips")
class TipController(
    val tipService: TipService,
) {

    @GetMapping("")
    fun getTips(): ResponseEntity<List<Tip>> {
        println("getTips")

        return ResponseEntity.ok(
            tipService.findAll()
        )
    }

    @PostMapping("")
    fun postTip(
        @RequestBody requestBody: PostTipDTO,
    ): ResponseEntity<Tip> {
        val tip = tipService.save(
            requestBody.title,
            requestBody.content,
        )

        return ResponseEntity.ok(tip)
    }
}