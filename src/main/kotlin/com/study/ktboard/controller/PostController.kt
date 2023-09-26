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
    fun getPosts(
        @PageableDefault(page = 0, size = 10) pageRequest: PageRequest,
        postSearchRequest: PostSearchRequest,
    ): Page<PostSummaryResponse> =
        postService.getPosts(pageRequest, postSearchRequest.toSearchRequestDto())
            .map(PostSummaryResponse::toPostSummaryResponse)

    @GetMapping("/{id}")
    fun getPost(
        @PathVariable id: Long,
    ): PostDetailResponse =
        postService.getPost(id)
            .let(PostDetailResponse::toPostDetailResponse)
}
