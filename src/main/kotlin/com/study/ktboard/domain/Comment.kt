package com.study.ktboard.domain

import com.study.ktboard.exception.CommentNotUpdatableException
import com.study.ktboard.service.dto.CommentUpdateRequestDto
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne

@Entity
class Comment(
    post: Post,
    content: String,
    createdBy: String
) : BaseEntity(
    createdBy = createdBy
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    var content: String = content
        protected set

    @ManyToOne
    var post: Post = post
        protected set

    fun update(commentUpdate: CommentUpdateRequestDto) {
        if(createdBy != commentUpdate.updatedBy) throw CommentNotUpdatableException()

        content = commentUpdate.content
        super.update(commentUpdate.updatedBy)
    }
}
