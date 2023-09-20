package com.study.ktboard.controller.dto

import java.time.LocalDateTime

data class PostDetailResponse(
    val id: Long,
    val title: String,
    val content: String,
    val createdBy: String,
    val createdAt: LocalDateTime,
)
