package com.study.ktboard.repository

import com.study.ktboard.domain.Comment
import org.springframework.data.jpa.repository.JpaRepository

interface CommentRepository : JpaRepository<Comment, Long>, PostRepositoryCustom
