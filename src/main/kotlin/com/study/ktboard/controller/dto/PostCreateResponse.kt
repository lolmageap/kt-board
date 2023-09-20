package com.study.ktboard.controller.dto

data class PostCreateResponse(
    val title: String,
    val content: String,
    val createdBy: String
)
