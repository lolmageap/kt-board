package com.study.ktboard.service

import com.study.ktboard.domain.Post
import com.study.ktboard.exception.PostNotDeletableException
import com.study.ktboard.exception.PostNotFoundException
import com.study.ktboard.exception.PostNotUpdatableException
import com.study.ktboard.repository.PostRepository
import com.study.ktboard.service.dto.PostCreateRequestDto
import com.study.ktboard.service.dto.PostUpdateRequestDto
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.longs.shouldBeGreaterThan
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull

@SpringBootTest
class PostServiceTest(
    private val postService: PostService,
    private val postRepository: PostRepository,
) : BehaviorSpec({
    given("게시글 생성시") {
        When("게시글 생성") {

            val postId = postService.createPost(
                PostCreateRequestDto(
                    title = "제목",
                    content = "내용",
                    createdBy = "cherhy",
                )
            )

            then("게시글이 정상적으로 생성됨을 확인한다.") {
                postId shouldBeGreaterThan 0L
                val post = postRepository.findByIdOrNull(postId)
                post shouldNotBe null
                post?.title shouldBe "제목"
                post?.content shouldBe "내용"
                post?.createdBy shouldBe "cherhy"
            }

        }
    }
    given("게시글 수정시") {
        val post = postRepository.save(Post(title = "title", content = "content", createdBy = "cherhy"))
        When("정상 수정시") {
            val updatedId = postService.updatePost(
                post.id,
                PostUpdateRequestDto(
                    title = "update title",
                    content = "update content",
                    updatedBy = "update createBy",
                )
            )

            then("게시글이 정상적으로 수정됨을 확인한다.") {
                post.id shouldBe updatedId
                val find = postRepository.findByIdOrNull(updatedId)
                find shouldNotBe null
                find?.title shouldBe "update title"
                find?.content shouldBe "update content"
                find?.createdBy shouldBe "update createBy"
            }
        }

        When("게시글이 없을 때") {
            then("게시글을 찾을수 없다는 예외가 발생한다.") {
                shouldThrow<PostNotFoundException> {
                    postService.updatePost(
                        -1L,
                        PostUpdateRequestDto(
                            title = "update title",
                            content = "update content",
                            updatedBy = "cherhy",
                        )
                    )
                }
            }
        }

        When("작성자가 동일하지 않으면") {
            then("수정할 수 없는 게시물입니다 예외가 발생한다.") {
                shouldThrow<PostNotUpdatableException> {
                    postService.updatePost(
                        post.id,
                        PostUpdateRequestDto(
                            title = "update title",
                            content = "update content",
                            updatedBy = "update createBy",
                        )
                    )
                }
            }
        }
    }

    given("게시글 삭제시") {
        When("정상 삭제시") {
            val post = postRepository.save(Post(title = "title", content = "content", createdBy = "cherhy"))
            val deletedId = postService.deletePost(
                post.id, "cherhy"
            )

            then("게시글이 정상적으로 삭제됨을 확인한다.") {
                post.id shouldBe deletedId
                val find = postRepository.findByIdOrNull(deletedId)
                find shouldBe null
            }
        }
        When("작성자가 동일하지 않으면") {
            val post = postRepository.save(Post(title = "title", content = "content", createdBy = "cherhy"))
            then("삭제할 수 없는 게시물입니다 에러가 발생한다.") {
                shouldThrow<PostNotDeletableException> {
                    postService.deletePost(post.id, "username")
                }
            }
        }
    }
})