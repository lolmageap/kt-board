package com.study.ktboard.service.dto

import com.study.ktboard.domain.Post
import java.time.LocalDateTime

data class PostDetailResponseDto(
    val id: Long,
    val title: String,
    val content: String,
    val createdBy: String,
    val createdAt: LocalDateTime,
) {
    companion object {

        fun toDetailResponseDto(post: Post) =
            PostDetailResponseDto(
                id = post.id,
                title = post.title,
                content = post.content,
                createdBy = post.createdBy,
                createdAt = post.createdAt,
            )

    }
}