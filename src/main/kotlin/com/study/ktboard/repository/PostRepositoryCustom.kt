package com.study.ktboard.repository

import com.study.ktboard.domain.Post
import com.study.ktboard.domain.QPost.post
import com.study.ktboard.service.dto.PostSearchRequestDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport

interface PostRepositoryCustom {
    fun findAll(pageRequest: PageRequest, postSearchRequestDto: PostSearchRequestDto): Page<Post>
}

class PostRepositoryCustomImpl : PostRepositoryCustom, QuerydslRepositorySupport(Post::class.java) {
    override fun findAll(pageRequest: PageRequest, postSearchRequestDto: PostSearchRequestDto): Page<Post> {
        val result = from(post)
            .where(
                postSearchRequestDto.title?.let { post.title.contains(it) },
                postSearchRequestDto.createdBy?.let { post.createdBy.eq(it) },
                postSearchRequestDto.tag?.let { post.tags.any().name.eq(it) },
            )
            .orderBy(post.createdAt.desc())
            .offset(pageRequest.offset)
            .limit(pageRequest.pageSize.toLong())
            .fetchResults()

        return PageImpl(result.results, pageRequest, result.total)
    }
}
