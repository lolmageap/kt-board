package com.study.ktboard.service.dto

import com.study.ktboard.domain.Comment

data class CommentResponseDto(
    val id: Long,
    val content: String,
    val createdBy: String,
    val createdAt: String,
) {
  companion object {
      fun toResponseDto(comment: Comment): CommentResponseDto {
          return CommentResponseDto(
              id = comment.id,
              content = comment.content,
              createdBy = comment.createdBy,
              createdAt = comment.createdAt.toString(),
          )
      }
  }
}