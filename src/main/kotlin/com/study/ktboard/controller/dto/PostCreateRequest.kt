package com.study.ktboard.controller.dto

import com.study.ktboard.service.dto.PostCreateRequestDto

data class PostCreateRequest(
    val title: String,
    val content: String,
    val createdBy: String,
    val tags: List<String> = emptyList(),
)

fun PostCreateRequest.toPostCreateRequestDto(): PostCreateRequestDto =
    PostCreateRequestDto(
        title = this.title,
        createdBy = this.createdBy,
        content = this.content,
        tags = this.tags,
    )
