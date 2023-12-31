package com.study.ktboard.repository

import com.study.ktboard.domain.Post
import com.study.ktboard.domain.QPost.post
import com.study.ktboard.domain.QTag.*
import com.study.ktboard.service.dto.PostSearchRequestDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport

interface PostRepositoryCustom {
    fun findAll(pageRequest: Pageable, postSearchRequestDto: PostSearchRequestDto): Page<Post>
}

class PostRepositoryCustomImpl : PostRepositoryCustom, QuerydslRepositorySupport(Post::class.java) {
    override fun findAll(pageRequest: Pageable, postSearchRequestDto: PostSearchRequestDto): Page<Post> =
        from(post)
            .leftJoin(post.tags, tag).fetchJoin()
            .where(
                postSearchRequestDto.title?.let { post.title.contains(it) },
                postSearchRequestDto.createdBy?.let { post.createdBy.eq(it) },
                postSearchRequestDto.tag?.let { post.tags.any().name.eq(it) },
            )
            .orderBy(post.createdAt.desc())
            .offset(pageRequest.offset)
            .limit(pageRequest.pageSize.toLong())
            .fetchResults()
            .let {
                PageImpl(it.results, pageRequest, it.total)
            }

}
