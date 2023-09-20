package com.study.ktboard.controller.dto

data class PostUpdateRequest(
    val title: String,
    val content: String,
    val modifiedBy: String
)
