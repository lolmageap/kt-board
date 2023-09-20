package com.study.ktboard.controller

import com.study.ktboard.controller.dto.PostCreateResponse
import com.study.ktboard.controller.dto.PostDetailResponse
import com.study.ktboard.controller.dto.PostUpdateRequest
import org.springframework.data.domain.Page
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
class PostController {

    @PostMapping("/posts")
    fun createPost(
        @RequestBody postCreateResponse: PostCreateResponse,
        ): Long {
            return 1L
    }

    @PutMapping("/posts/{id}")
    fun updatePost(
            @PathVariable id: Long,
            @RequestBody postUpdateRequest: PostUpdateRequest,
        ): Long {
            return id
    }

    @DeleteMapping("/posts/{id}")
    fun deletePost(
            @PathVariable id: Long,
            @RequestParam createdBy: String
        ): Long {
            println(createdBy)
            return id
    }

    @GetMapping("/posts")
    fun getPost(): Page<PostDetailResponse> {
            return Page.empty()
//        (
//                PostDetailResponse(1, "title", "content", "cherry", LocalDateTime.now()),
//                PostDetailResponse(2, "제목", "내용", "정철희", LocalDateTime.now()),
//            )
    }

    @GetMapping("/posts/{id}")
    fun getPosts(
            @PathVariable id: Long,
        ): PostDetailResponse {
            return PostDetailResponse(1, "title", "content", "정철희", LocalDateTime.now())
    }

}