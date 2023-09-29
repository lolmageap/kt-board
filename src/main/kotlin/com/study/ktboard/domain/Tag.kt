package com.study.ktboard.domain

import jakarta.persistence.*

@Entity
@Table(indexes = [Index(name = "idx_post_id", columnList = "post_id")])
class Tag(
    name: String,
    post: Post,
    createdBy: String
) : BaseEntity(createdBy) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L

    var name: String = name
        protected set

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = ForeignKey(ConstraintMode.NO_CONSTRAINT))
    var post: Post = post
        protected set

}
