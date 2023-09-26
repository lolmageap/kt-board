package com.study.ktboard.service.dto

import com.study.ktboard.domain.Post

data class PostSummaryResponseDto(
    val id: Long,
    val title: String,
    val content: String,
    val createdBy: String,
    val createdAt: String,
    val firstTag: String?
) {
    companion object {

        fun toSummaryResponseDto(post: Post) =
            PostSummaryResponseDto(
                id = post.id,
                title = post.title,
                content = post.content,
                createdBy = post.createdBy,
                createdAt = post.createdAt.toString(),
                firstTag = post.tags.firstOrNull()?.name,
            )
    }
}
