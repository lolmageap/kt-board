package com.study.ktboard.controller.dto

import com.study.ktboard.service.dto.PostDetailResponseDto

data class PostDetailResponse(
    val id: Long,
    val title: String,
    val content: String,
    val createdBy: String,
    val createdAt: String,
    val comments: List<CommentResponse> = emptyList(),
    val tags: List<String> = emptyList(),
    val likeCount: Long = 0,
) {
    companion object {
        fun toPostDetailResponse(postDetailResponseDto: PostDetailResponseDto): PostDetailResponse =
            PostDetailResponse(
                id = postDetailResponseDto.id,
                title = postDetailResponseDto.title,
                content = postDetailResponseDto.content,
                createdBy = postDetailResponseDto.createdBy,
                createdAt = postDetailResponseDto.createdAt.toString(),
                comments = postDetailResponseDto.comments.map(CommentResponse::toResponse),
                likeCount = postDetailResponseDto.likeCount,
                )

    }
}
