package com.study.ktboard.service

import com.study.ktboard.domain.Like
import com.study.ktboard.exception.PostNotFoundException
import com.study.ktboard.repository.LikeRepository
import com.study.ktboard.repository.PostRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class LikeService(
    private val likeRepository: LikeRepository,
    private val postRepository: PostRepository,
) {

    fun createLike(postId: Long, createdBy: String): Long {
        val post = postRepository.findByIdOrNull(postId) ?: throw PostNotFoundException()
        return likeRepository.save(
            Like(post, createdBy)
        ).id
    }


}
