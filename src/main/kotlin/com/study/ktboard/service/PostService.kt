package com.study.ktboard.service

import com.study.ktboard.exception.PostNotFoundException
import com.study.ktboard.repository.PostRepository
import com.study.ktboard.service.dto.PostCreateRequestDto
import com.study.ktboard.service.dto.PostUpdateRequestDto
import com.study.ktboard.service.dto.toPost
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class PostService(
    private val postRepository: PostRepository
) {

    @Transactional(readOnly = false)
    fun createPost(requestDto: PostCreateRequestDto): Long {
        return postRepository.save(requestDto.toPost()).id
    }

    @Transactional(readOnly = false)
    fun updatePost(id: Long, requestDto: PostUpdateRequestDto): Long {
        val post = postRepository.findByIdOrNull(id)
            ?:throw PostNotFoundException()

        post.update(requestDto)
        return id
    }

}