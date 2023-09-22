package com.study.ktboard.service

import com.study.ktboard.exception.PostNotDeletableException
import com.study.ktboard.exception.PostNotFoundException
import com.study.ktboard.repository.PostRepository
import com.study.ktboard.service.dto.PostCreateRequestDto
import com.study.ktboard.service.dto.PostDetailResponseDto
import com.study.ktboard.service.dto.PostUpdateRequestDto
import com.study.ktboard.service.dto.toPost
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class PostService(
    private val postRepository: PostRepository
) {

    @Transactional(readOnly = false)
    fun createPost(requestDto: PostCreateRequestDto): Long =
        postRepository.save(requestDto.toPost()).id


    @Transactional(readOnly = false)
    fun updatePost(id: Long, requestDto: PostUpdateRequestDto): Long {
        val post = postRepository.findByIdOrNull(id) ?: throw PostNotFoundException()

        post.update(requestDto)
        return id
    }

    @Transactional(readOnly = false)
    fun deletePost(id: Long, deletedBy: String): Long {
        val post = postRepository.findByIdOrNull(id) ?:throw PostNotFoundException()

        if (post.createdBy != deletedBy) throw PostNotDeletableException()

        postRepository.delete(post)
        return id
    }

    fun getPost(id: Long) : PostDetailResponseDto =
        postRepository.findByIdOrNull(id)
            ?.let(PostDetailResponseDto::toDetailResponseDto)
            ?: throw PostNotFoundException()

    fun getPosts(pageable: PageRequest): Page<PostDetailResponseDto> =
        postRepository.findAll(pageable)
            .let { PostDetailResponseDto::toDetailResponseDto }


}