package com.study.ktboard.controller.dto

import com.study.ktboard.service.dto.PostSearchRequestDto
import org.springframework.web.bind.annotation.RequestParam

data class PostSearchRequest(
    @RequestParam
    val title: String? = null,
    @RequestParam
    val createdBy: String? = null,
    @RequestParam
    val tag: String? = null,
)

fun PostSearchRequest.toSearchRequestDto() =
    PostSearchRequestDto(
        title = title,
        createdBy = createdBy,
        tag = tag,
    )