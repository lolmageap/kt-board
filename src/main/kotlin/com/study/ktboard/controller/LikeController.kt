package com.study.ktboard.controller

import com.study.ktboard.controller.dto.*
import com.study.ktboard.service.LikeService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/posts/{postId}/likes")
class LikeController(
    private val likeService: LikeService
) {

    @PostMapping
    fun createLike(
        @PathVariable postId: Long,
        @RequestParam createdBy: String,
    ) = likeService.createLike(postId, createdBy)

}
