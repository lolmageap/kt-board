package com.study.ktboard.controller

import com.study.ktboard.controller.dto.*
import com.study.ktboard.service.PostService
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
class PostController(
    private val postService: PostService
) {

    @PostMapping("/posts")
    fun createPost(
        @RequestBody postCreateRequest: PostCreateRequest,
        ): Long {
            return postService.createPost( postCreateRequest.toPostCreateRequestDto() )
    }

    @PutMapping("/posts/{id}")
    fun updatePost(
            @PathVariable id: Long,
            @RequestBody postUpdateRequest: PostUpdateRequest,
        ): Long {
            return postService.updatePost( id, postUpdateRequest.toPostUpdateRequestDto() )
    }

    @DeleteMapping("/posts/{id}")
    fun deletePost(
            @PathVariable id: Long,
            @RequestParam deletedBy: String
        ): Long {
            return postService.deletePost( id, deletedBy )
    }

    @GetMapping("/posts")
    fun getPost(): Page<PostDetailRequest> {
            return Page.empty()
//        (
//                PostDetailResponse(1, "title", "content", "cherry", LocalDateTime.now()),
//                PostDetailResponse(2, "제목", "내용", "정철희", LocalDateTime.now()),
//            )
    }

    @GetMapping("/posts/{id}")
    fun getPosts(
            @PathVariable id: Long,
        ): PostDetailRequest {
            return PostDetailRequest(1, "title", "content", "정철희", LocalDateTime.now())
    }

}