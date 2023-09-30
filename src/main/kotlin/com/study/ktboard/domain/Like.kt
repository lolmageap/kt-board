package com.study.ktboard.domain

import jakarta.persistence.*

@Entity
@Table(name = "likes", indexes = [Index(name = "like_idx_post_id", columnList = "post_id")])
class Like(
    post: Post,
    createdBy: String,
) : BaseEntity(
    createdBy = createdBy
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = ForeignKey(ConstraintMode.NO_CONSTRAINT))
    var post: Post = post
        protected set

}
