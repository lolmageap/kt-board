package com.study.ktboard.event.dto

data class LikeEvent(
    val postId: Long,
    val createdBy: String,
)