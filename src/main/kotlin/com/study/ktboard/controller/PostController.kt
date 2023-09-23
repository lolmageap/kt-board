package com.study.ktboard.controller

import com.study.ktboard.controller.dto.*
import com.study.ktboard.service.PostService
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/posts")
class PostController(
    private val postService: PostService
) {

    @PostMapping
    fun createPost(
        @RequestBody postCreateRequest: PostCreateRequest
    ): Long {
        return postService.createPost(postCreateRequest.toPostCreateRequestDto())
    }

    @PutMapping("/{id}")
    fun updatePost(
        @PathVariable id: Long,
        @RequestBody postUpdateRequest: PostUpdateRequest
    ): Long {
        return postService.updatePost(id, postUpdateRequest.toPostUpdateRequestDto())
    }

    @DeleteMapping("/{id}")
    fun deletePost(
        @PathVariable id: Long,
        @RequestParam deletedBy: String
    ): Long {
        return postService.deletePost(id, deletedBy)
    }

    @GetMapping
    fun getPost(
        @PageableDefault(page = 0, size = 10) pageRequest: PageRequest
    ): Page<PostDetailResponse> =
        postService.getPosts(pageRequest)
            .map(PostDetailResponse::toPostDetailResponse)

    @GetMapping("/{id}")
    fun getPosts(
        @PathVariable id: Long
    ): PostDetailResponse =
        postService.getPost(id)
            .let(PostDetailResponse::toPostDetailResponse)
}
