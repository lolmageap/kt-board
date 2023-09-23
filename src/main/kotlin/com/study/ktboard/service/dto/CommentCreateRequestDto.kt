package com.study.ktboard.service.dto

import com.study.ktboard.domain.Comment
import com.study.ktboard.domain.Post

data class CommentCreateRequestDto(
    val content: String,
    val createdBy: String,
)

fun CommentCreateRequestDto.toComment(post: Post): Comment =
    Comment(
        post = post,
        content = content,
        createdBy = createdBy
    )