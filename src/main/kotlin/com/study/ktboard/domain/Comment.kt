package com.study.ktboard.domain

import com.study.ktboard.exception.CommentNotUpdatableException
import com.study.ktboard.service.dto.CommentUpdateRequestDto
import jakarta.persistence.*

@Entity
@Table(indexes = [Index(name = "comment_idx_post_id", columnList = "post_id")])
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
    @JoinColumn(foreignKey = ForeignKey(ConstraintMode.NO_CONSTRAINT))
    var post: Post = post
        protected set

    fun update(commentUpdate: CommentUpdateRequestDto) {
        if(createdBy != commentUpdate.updatedBy) throw CommentNotUpdatableException()

        content = commentUpdate.content
        super.update(commentUpdate.updatedBy)
    }
}
