package com.study.ktboard.service.dto

data class CommentUpdateRequestDto(
    val content: String,
    val updatedBy: String,
)