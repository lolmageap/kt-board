package com.study.ktboard.service

import com.study.ktboard.exception.PostNotDeletableException
import com.study.ktboard.exception.PostNotFoundException
import com.study.ktboard.repository.PostRepository
import com.study.ktboard.service.dto.*
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class PostService(
    private val postRepository: PostRepository
) {

    fun createPost(requestDto: PostCreateRequestDto): Long =
        postRepository.save(requestDto.toPost()).id

    fun updatePost(id: Long, requestDto: PostUpdateRequestDto): Long {
        val post = postRepository.findByIdOrNull(id) ?: throw PostNotFoundException()

        post.update(requestDto)
        return id
    }

    fun deletePost(id: Long, deletedBy: String): Long {
        val post = postRepository.findByIdOrNull(id) ?: throw PostNotFoundException()

        if (post.createdBy != deletedBy) throw PostNotDeletableException()

        postRepository.delete(post)
        return id
    }

    fun getPost(id: Long): PostDetailResponseDto =
        postRepository.findByIdOrNull(id)
            ?.let(PostDetailResponseDto::toDetailResponseDto)
            ?: throw PostNotFoundException()

    fun getPosts(pageRequest: PageRequest): Page<PostDetailResponseDto> =
        postRepository.findAll(pageRequest)
            .map(PostDetailResponseDto::toDetailResponseDto)

    fun getPosts(pageRequest: PageRequest, postSearchRequestDto: PostSearchRequestDto): Page<PostDetailResponseDto> =
        postRepository.findAll(pageRequest, postSearchRequestDto)
            .map(PostDetailResponseDto::toDetailResponseDto)
}
