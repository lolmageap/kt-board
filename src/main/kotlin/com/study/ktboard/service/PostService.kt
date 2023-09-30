package com.study.ktboard.service

import com.study.ktboard.exception.PostNotDeletableException
import com.study.ktboard.exception.PostNotFoundException
import com.study.ktboard.repository.PostRepository
import com.study.ktboard.service.dto.*
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class PostService(
    private val postRepository: PostRepository,
    private val likeService: LikeService,
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
            ?.let {
                PostDetailResponseDto.toDetailResponseDto(
                    post = it,
                    likeCount = likeService.countLike(it.id)
                )
            }
            ?: throw PostNotFoundException()

    fun getPosts(pageRequest: Pageable): Page<PostSummaryResponseDto> =
        postRepository.findAll(pageRequest)
            .map(PostSummaryResponseDto::toSummaryResponseDto)

    fun getPosts(pageRequest: Pageable, postSearchRequestDto: PostSearchRequestDto): Page<PostSummaryResponseDto> =
        postRepository.findAll(pageRequest, postSearchRequestDto)
            .map {
                PostSummaryResponseDto.toSummaryResponseDto(
                    post = it,
                    likeCount = likeService.countLike(it.id)
                )
            }

}
