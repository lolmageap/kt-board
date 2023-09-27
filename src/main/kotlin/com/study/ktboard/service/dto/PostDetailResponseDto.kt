package com.study.ktboard.service.dto

import com.study.ktboard.domain.Post
import java.time.LocalDateTime

data class PostDetailResponseDto(
    val id: Long,
    val title: String,
    val content: String,
    val createdBy: String,
    val createdAt: LocalDateTime,
    val comments: List<CommentResponseDto>,
    val tags: List<String>,
    val likeCount: Long = 0,
) {
    companion object {

        fun toDetailResponseDto(post: Post, likeCount: Long) =
            PostDetailResponseDto(
                id = post.id,
                title = post.title,
                content = post.content,
                createdBy = post.createdBy,
                createdAt = post.createdAt,
                comments = post.comments.map(CommentResponseDto::toResponseDto),
                tags = post.tags.map { it.name },
                likeCount = likeCount
            )
    }
}
