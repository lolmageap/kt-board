package com.study.ktboard.service

import com.study.ktboard.event.dto.LikeEvent
import com.study.ktboard.repository.LikeRepository
import com.study.ktboard.util.RedisUtil
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class LikeService(
    private val likeRepository: LikeRepository,
    private val redisUtil: RedisUtil,
    private val applicationEventPublisher: ApplicationEventPublisher,
) {

    fun createLike(postId: Long, createdBy: String) {
        applicationEventPublisher.publishEvent( LikeEvent(postId, createdBy) )
    }

    fun countLike(postId: Long): Long {
        redisUtil.getCount( redisUtil.getLikeCountKey(postId) )?.let { return it }
        with(likeRepository.countByPostId(postId)) {
            redisUtil.setData( redisUtil.getLikeCountKey(postId) , this)
            return this
        }
    }

}
