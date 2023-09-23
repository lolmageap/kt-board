package com.study.ktboard.service

import com.study.ktboard.exception.CommentNotDeletableException
import com.study.ktboard.exception.CommentNotFoundException
import com.study.ktboard.exception.PostNotFoundException
import com.study.ktboard.repository.CommentRepository
import com.study.ktboard.repository.PostRepository
import com.study.ktboard.service.dto.CommentCreateRequestDto
import com.study.ktboard.service.dto.CommentUpdateRequestDto
import com.study.ktboard.service.dto.toComment
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class CommentService(
    private val commentRepository: CommentRepository,
    private val postRepository: PostRepository,
) {

    fun createComment(postId: Long, commentCreateRequestDto: CommentCreateRequestDto): Long {
        val post = postRepository.findByIdOrNull(postId) ?: throw PostNotFoundException()
        return commentRepository.save( commentCreateRequestDto.toComment(post) ).id
    }

    fun updateComment(id: Long, commentUpdate: CommentUpdateRequestDto): Long =
        commentRepository.findByIdOrNull(id)
            ?.run {
                update(commentUpdate)
                id
            }
            ?: throw CommentNotFoundException()

    fun deleteComment(id: Long, deletedBy: String): Long =
        commentRepository.findByIdOrNull(id)
            ?.let {
                if (it.createdBy != deletedBy) { throw CommentNotDeletableException() }
                commentRepository.delete(it)
                id
            }
            ?: throw CommentNotFoundException()

}
