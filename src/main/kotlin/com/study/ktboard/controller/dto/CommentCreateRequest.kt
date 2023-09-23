package com.study.ktboard.controller.dto

import com.study.ktboard.service.dto.CommentCreateRequestDto

data class CommentCreateRequest(
    val content: String,
    val createdBy: String
)

fun CommentCreateRequest.toCommentCreateRequestDto() =
    CommentCreateRequestDto(
        content = content,
        createdBy = createdBy,
    )