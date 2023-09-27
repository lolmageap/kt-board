package com.study.ktboard.service.dto

import com.study.ktboard.domain.Post

data class PostSummaryResponseDto(
    val id: Long,
    val title: String,
    val content: String,
    val createdBy: String,
    val createdAt: String,
    val firstTag: String?,
    val likeCount: Long = 0,
) {
    companion object {

        fun toSummaryResponseDto(post: Post, likeCount: Long = 0L) =
            PostSummaryResponseDto(
                id = post.id,
                title = post.title,
                content = post.content,
                createdBy = post.createdBy,
                createdAt = post.createdAt.toString(),
                firstTag = post.tags.firstOrNull()?.name,
                likeCount = likeCount,
            )
    }
}
