package com.study.ktboard.repository

import com.study.ktboard.domain.Post
import org.springframework.data.jpa.repository.JpaRepository

interface PostRepository : JpaRepository<Post, Long>, PostRepositoryCustom {

}