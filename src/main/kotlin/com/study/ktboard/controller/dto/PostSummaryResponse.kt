package com.study.ktboard.controller.dto

import com.study.ktboard.service.dto.PostSummaryResponseDto

data class PostSummaryResponse(
    val id: Long,
    val title: String,
    val content: String,
    val createdBy: String,
    val createdAt: String,
    val firstTag: String?,
) {
    companion object {
        fun toPostSummaryResponse(postSummaryResponseDto: PostSummaryResponseDto): PostSummaryResponse =
            PostSummaryResponse(
                id = postSummaryResponseDto.id,
                title = postSummaryResponseDto.title,
                content = postSummaryResponseDto.content,
                createdBy = postSummaryResponseDto.createdBy,
                createdAt = postSummaryResponseDto.createdAt,
                firstTag = postSummaryResponseDto.firstTag,
            )

    }
}
