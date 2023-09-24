package com.study.ktboard.service

import com.study.ktboard.domain.Comment
import com.study.ktboard.domain.Post
import com.study.ktboard.exception.CommentNotDeletableException
import com.study.ktboard.exception.CommentNotUpdatableException
import com.study.ktboard.exception.PostNotFoundException
import com.study.ktboard.repository.CommentRepository
import com.study.ktboard.repository.PostRepository
import com.study.ktboard.service.dto.CommentCreateRequestDto
import com.study.ktboard.service.dto.CommentUpdateRequestDto
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.longs.shouldBeGreaterThan
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull

@SpringBootTest
class CommentServiceTest(
    private val commentService: CommentService,
    private val commentRepository: CommentRepository,
    private val postRepository: PostRepository,
    private val postService: PostService,
) : BehaviorSpec({
    given("댓글 생성시") {
        val post = postRepository.save(
            Post(
                title = "제목",
                content = "게시물 내용",
                createdBy = "게시물 생성자",
            )
        )
        val commentCreate = CommentCreateRequestDto(
            content = "댓글 내용",
            createdBy = "댓글 생성자"
        )

        When("인풋이 정상적으로 들어오면") {
            val commentId = commentService.createComment(post.id, commentCreate)
            then("정상적으로 생성됨을 확인한다.") {
                commentId shouldBeGreaterThan 0L
                val find = commentRepository.findByIdOrNull(commentId)
                find shouldNotBe null
                find?.content shouldBe "댓글 내용"
                find?.createdBy shouldBe "댓글 생성자"
            }
        }

        When("게시글이 존재하지 않으면") {
            then("게시글이 존재하지 않음 예외가 발생한다.") {
                shouldThrow<PostNotFoundException> { commentService.createComment(-1L, commentCreate) }
            }
        }
    }

    given("댓글 수정시") {
        val post = postRepository.save(
            Post(
                title = "제목",
                content = "게시물 내용",
                createdBy = "게시물 생성자",
            )
        )

        val save = commentRepository.save(
            Comment(
                content = "댓글 내용",
                createdBy = "댓글 생성자",
                post = post,
            )
        )

        val commentUpdate = CommentUpdateRequestDto(
            content = "수정된 댓글 내용",
            updatedBy = "댓글 생성자"
        )

        val failUpdate = CommentUpdateRequestDto(
            content = "수정된 댓글 내용",
            updatedBy = "실패 수정자"
        )

        When("인풋이 정상적으로 들어오면") {
            val updatedId = commentService.updateComment(post.id, commentUpdate)
            then("정상적으로 수정됨을 확인한다.") {
                updatedId shouldBe save.id
                val find = commentRepository.findByIdOrNull(updatedId)
                find shouldNotBe null
                find?.content shouldBe "수정된 댓글 내용"
                find?.createdBy shouldBe "댓글 생성자"
            }
        }

        When("작성자와 수정자가 다르면") {
            then("수정할 수 없는 게시물 예외가 발생한다.") {
                shouldThrow<CommentNotUpdatableException> { commentService.updateComment(save.id, failUpdate) }
            }
        }
    }

    given("댓글 삭제시") {
        val post = postRepository.save(
            Post(
                title = "제목",
                content = "게시물 내용",
                createdBy = "게시물 생성자",
            )
        )

        val save = commentRepository.save(
            Comment(
                content = "댓글 내용",
                createdBy = "댓글 생성자",
                post = post,
            )
        )

        When("인풋이 정상적으로 들어오면") {
            val deletedId = commentService.deleteComment(save.id, save.createdBy)
            then("정상적으로 삭제됨을 확인한다.") {
                deletedId shouldBe save.id
                commentRepository.findByIdOrNull(deletedId) shouldBe null
            }
        }

        When("작성자와 삭제자가 다르면") {
            then("삭제할 수 없는 게시물 예외가 발생한다.") {
                shouldThrow<CommentNotDeletableException> { commentService.deleteComment(save.id, "삭제자") }
            }
        }
    }

    given("댓글 상세 조회시") {
        val post = postRepository.save(
            Post(
                title = "제목",
                content = "게시물 내용",
                createdBy = "게시물 생성자",
            )
        )

        When("댓글 추가시") {
            val saveComment = commentRepository.saveAll(
                listOf(
                    Comment(content = "댓글1 내용", createdBy = "댓글1 생성자", post = post),
                    Comment(content = "댓글2 내용", createdBy = "댓글2 생성자", post = post),
                    Comment(content = "댓글3 내용", createdBy = "댓글3 생성자", post = post),
                )
            )

            val findPost = postService.getPost(post.id)

            then("댓글이 함께 조회됨을 확인한다.") {
                findPost.comments.size shouldBe 3
                findPost.comments[0].content shouldBe "댓글1 내용"
                findPost.comments[1].content shouldBe "댓글2 내용"
                findPost.comments[2].content shouldBe "댓글3 내용"
                findPost.comments[0].createdBy shouldBe "댓글1 생성자"
                findPost.comments[1].createdBy shouldBe "댓글2 생성자"
                findPost.comments[2].createdBy shouldBe "댓글3 생성자"
            }
        }

    }

})
