package com.study.ktboard.controller.dto

import com.study.ktboard.service.dto.CommentResponseDto

data class CommentResponse(
    val id: Long,
    val content: String,
    val createdBy: String,
    val createdAt: String
) {
    companion object {
        fun toResponse(commentResponseDto: CommentResponseDto): CommentResponse =
            CommentResponse(
                id = commentResponseDto.id,
                content = commentResponseDto.content,
                createdBy = commentResponseDto.createdBy,
                createdAt = commentResponseDto.createdAt,
            )

    }
}
