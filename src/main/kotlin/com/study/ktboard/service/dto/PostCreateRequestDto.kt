package com.study.ktboard.service.dto

import com.study.ktboard.domain.Post

data class PostCreateRequestDto(
    val title: String,
    val content: String,
    val createdBy: String
)

fun PostCreateRequestDto.toPost(): Post =
    Post(
        title = this.title,
        createdBy = this.createdBy,
        content = this.content
    )
