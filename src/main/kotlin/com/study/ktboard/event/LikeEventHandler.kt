package com.study.ktboard.event

import com.study.ktboard.domain.Like
import com.study.ktboard.event.dto.LikeEvent
import com.study.ktboard.exception.PostNotFoundException
import com.study.ktboard.repository.LikeRepository
import com.study.ktboard.repository.PostRepository
import com.study.ktboard.util.RedisUtil
import org.springframework.data.repository.findByIdOrNull
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import org.springframework.transaction.event.TransactionalEventListener

@Service
class LikeEventHandler(
    private val likeRepository: LikeRepository,
    private val postRepository: PostRepository,
    private val redisUtil: RedisUtil,
    ) {

    @Async
    @TransactionalEventListener(LikeEvent::class)
    fun handle(event: LikeEvent) {
        val post = postRepository.findByIdOrNull(event.postId) ?: throw PostNotFoundException()
        redisUtil.increment( redisUtil.getLikeCountKey(event.postId) )
        likeRepository.save( Like(post, event.createdBy) )
    }

}