package com.study.ktboard.controller

import com.study.ktboard.controller.dto.*
import com.study.ktboard.service.CommentService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/posts/{postId}/comments")
class CommentController(
    private val commentService: CommentService,
) {

    @PostMapping
    fun createComment(
        @PathVariable postId: Long,
        @RequestBody commentCreateRequest: CommentCreateRequest
    ): Long =
        commentService.createComment(postId, commentCreateRequest.toCommentCreateRequestDto())


    @PutMapping("/{id}")
    fun updateComment(
        @PathVariable postId: Long,
        @PathVariable id: Long,
        @RequestBody commentUpdateRequest: CommentUpdateRequest
    ): Long =
        commentService.updateComment(postId, commentUpdateRequest.toCommentUpdateRequestDto())


    @DeleteMapping("/{id}")
    fun deleteComment(
        @PathVariable postId: Long,
        @PathVariable id: Long,
        @RequestParam deletedBy: String
    ): Long =
        commentService.deleteComment(id, deletedBy)

}