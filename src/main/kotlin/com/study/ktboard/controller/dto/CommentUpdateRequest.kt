package com.study.ktboard.controller.dto

import com.study.ktboard.service.dto.CommentUpdateRequestDto


data class CommentUpdateRequest(
    val content: String,
    val updatedBy: String
)

fun CommentUpdateRequest.toCommentUpdateRequestDto() =
    CommentUpdateRequestDto(
        content = content,
        updatedBy = updatedBy,
    )