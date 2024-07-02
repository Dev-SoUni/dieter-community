package com.devsy.dieter_community.controller

import com.devsy.dieter_community.domain.Tip
import com.devsy.dieter_community.dto.TipPatchDTO
import com.devsy.dieter_community.dto.TipPostDTO
import com.devsy.dieter_community.dto.TipResponseDTO
import com.devsy.dieter_community.service.TipService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/tips")
class TipController(
    val tipService: TipService,
) {

    @GetMapping("")
    fun getTips(): ResponseEntity<List<TipResponseDTO>> {
        return ResponseEntity.ok(
            tipService
                .findAll()
                .map {
                    TipResponseDTO(
                        id = it.id!!,
                        title = it.title,
                        content = it.content,
                        updatedAt = it.updatedAt,
                        createdAt = it.createdAt,
                    )
                }
        )
    }

    @PostMapping("")
    fun postTip(
        @RequestBody requestBody: TipPostDTO,
    ): ResponseEntity<Tip> {
        val postedTip = tipService.post(
            requestBody.title,
            requestBody.content,
        )

        return ResponseEntity.ok(postedTip)
    }

    @PatchMapping("/{id}")
    fun patchTip(
        @PathVariable(name = "id") id: String,
        @RequestBody requestBody: TipPatchDTO,
    ): ResponseEntity<Tip> {
        val patchedTip = tipService.patch(
            id = id,
            title = requestBody.title,
            content = requestBody.content,
        )

        return ResponseEntity.ok(patchedTip)
    }

    @DeleteMapping("/{id}")
    fun deleteTip(
     @PathVariable(name = "id") id: String
    ): ResponseEntity<HttpStatus> {
        tipService.delete(id)

        return ResponseEntity(HttpStatus.NO_CONTENT)
    }
}