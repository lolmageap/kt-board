package com.study.ktboard.controller.dto

import com.study.ktboard.service.dto.PostUpdateRequestDto

data class PostUpdateRequest(
    val title: String,
    val content: String,
    val updatedBy: String,
    val tags: List<String> = emptyList(),
)

fun PostUpdateRequest.toPostUpdateRequestDto(): PostUpdateRequestDto =
    PostUpdateRequestDto(
        title = this.title,
        updatedBy = this.updatedBy,
        content = this.content,
        tags = this.tags,
    )
