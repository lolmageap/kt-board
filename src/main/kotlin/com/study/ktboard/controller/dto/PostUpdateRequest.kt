package com.study.ktboard.controller.dto

import com.study.ktboard.service.dto.PostUpdateRequestDto

data class PostUpdateRequest(
    val title: String,
    val content: String,
    val modifiedBy: String
)

fun PostUpdateRequest.toPostUpdateRequestDto(): PostUpdateRequestDto =
    PostUpdateRequestDto(
        title = this.title,
        updatedBy = this.modifiedBy,
        content = this.content,
    )

