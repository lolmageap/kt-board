package com.study.ktboard.service

import com.study.ktboard.domain.Post
import com.study.ktboard.domain.Tag
import com.study.ktboard.exception.PostNotDeletableException
import com.study.ktboard.exception.PostNotFoundException
import com.study.ktboard.exception.PostNotUpdatableException
import com.study.ktboard.repository.PostRepository
import com.study.ktboard.repository.TagRepository
import com.study.ktboard.service.dto.PostCreateRequestDto
import com.study.ktboard.service.dto.PostSearchRequestDto
import com.study.ktboard.service.dto.PostUpdateRequestDto
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.longs.shouldBeGreaterThan
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.string.shouldContain
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull

@SpringBootTest
class PostServiceTest(
    private val postService: PostService,
    private val postRepository: PostRepository,
    private val tagRepository: TagRepository,
    private val likeService: LikeService,
) : BehaviorSpec({

    given("게시글 생성시") {
        When("게시글 인풋이 정상적으로 들어오면") {

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
        When("태그가 추가되면") {

            val postId = postService.createPost(
                PostCreateRequestDto(
                    title = "제목",
                    content = "내용",
                    createdBy = "cherhy",
                    tags = listOf("tag1", "tag2")
                )
            )

            then("태그가 정상적으로 추가됨을 확인한다.") {
                val tags = tagRepository.findByPostId(postId)
                tags.size shouldBe 2
                tags[0].name shouldBe "tag1"
                tags[1].name shouldBe "tag2"
            }
        }
    }

    given("게시글 수정시") {
        val post = postRepository.save(Post(title = "title", content = "content", createdBy = "cherhy", tags = listOf("tag1", "tag2")))
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

        When("태그가 수정되었을 때") {
            val updateId = postService.updatePost(
                post.id,
                PostUpdateRequestDto(
                    title = "update 제목",
                    content = "update 내용",
                    updatedBy = "cherhy",
                    tags = listOf("tag1", "tag2", "tag3")
                )
            )

            then("태그가 정상적으로 추가됨을 확인한다.") {
                val tags = tagRepository.findByPostId(updateId)
                tags.size shouldBe 3
                tags[0].name shouldBe "tag1"
                tags[1].name shouldBe "tag2"
                tags[2].name shouldBe "tag3"
            }

            then("태그가 순서가 변경되었을때 정상적으로 변경됨을 확인한다.") {
                postService.updatePost(
                    post.id,
                    PostUpdateRequestDto(
                        title = "update 제목",
                        content = "update 내용",
                        updatedBy = "cherhy",
                        tags = listOf("tag3", "tag1", "tag2")
                    )
                )

                val tags = tagRepository.findByPostId(updateId)
                tags.size shouldBe 3
                tags[0].name shouldBe "tag3"
                tags[1].name shouldBe "tag1"
                tags[2].name shouldBe "tag2"
            }
        }
    }

    given("게시글 삭제시") {
        When("정상 삭제시") {
            val post = postRepository.save(Post(title = "title", content = "content", createdBy = "cherhy"))
            val deletedId = postService.deletePost(
                post.id,
                "cherhy",
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

    given("게시글 상세조회시") {
        val post = postRepository.save(Post(title = "title", content = "content", createdBy = "cherhy"))
        tagRepository.saveAll(
            listOf(
                Tag(name="tag1", post=post, createdBy = "cherhy"),
                Tag(name="tag2", post=post, createdBy = "cherhy"),
                Tag(name="tag3", post=post, createdBy = "cherhy"),
            )
        )

        likeService.createLike(post.id, "cherhy1")
        likeService.createLike(post.id, "cherhy2")
        likeService.createLike(post.id, "cherhy3")

        When("정상 조회시") {
            val find = postService.getPost(post.id)

            then("게시글이 정상적으로 조회됨을 확인한다.") {
                find.id shouldBe post.id
                find.title shouldBe "title"
                find.content shouldBe "content"
                find.createdBy shouldBe "cherhy"
            }

            then("태그가 정상적으로 조회됨을 확인한다.") {
                find.tags.size shouldBe 3
                find.tags[0] shouldBe "tag1"
                find.tags[1] shouldBe "tag2"
                find.tags[2] shouldBe "tag3"
            }

            then("좋아요 개수가 정상적으로 조회됨을 확인한다.") {
                find.likeCount shouldBe 3
            }
        }

        When("게시글이 없으면") {
            then("게시글을 찾을 수 없다는 예외가 발생한다.") {
                shouldThrow<PostNotFoundException> {
                    postService.getPost(-1L)
                }
            }
        }
    }

    given("게시글 목록조회시") {
        postRepository.saveAll(
            listOf(
                Post(title = "title1", content = "content", createdBy = "cherhy", tags = listOf("tag1", "tag2")),
                Post(title = "title2", content = "content", createdBy = "cherhy", tags = listOf("tag1", "tag2")),
                Post(title = "title3", content = "content", createdBy = "cherhy", tags = listOf("tag1", "tag2")),
                Post(title = "title4", content = "content", createdBy = "cherhy", tags = listOf("tag1", "tag2")),
                Post(title = "title5", content = "content", createdBy = "cherhy", tags = listOf("tag1", "tag2")),
                Post(title = "title6", content = "content", createdBy = "cherhy2", tags = listOf("tag1", "tag2")),
                Post(title = "title7", content = "content", createdBy = "cherhy2", tags = listOf("tag1", "tag2")),
                Post(title = "title8", content = "content", createdBy = "cherhy2", tags = listOf("tag1", "tag2")),
                Post(title = "title9", content = "content", createdBy = "cherhy2", tags = listOf("tag1", "tag2")),
                Post(title = "title10", content = "content", createdBy = "cherhy2", tags = listOf("tag1", "tag2")),
            )
        )

        When("정상 조회시") {
            val all = postService.getPosts(PageRequest.of(0, 5))
            then("게시글 페이지가 반환된다.") {
                all.number shouldBe 0
                all.size shouldBe 5
                all.content.size shouldBe 5
                all.content[0].title shouldContain "title"
                all.content[0].createdBy shouldContain "cherhy"
            }
        }

        When("타이틀로 검색") {
            val all = postService.getPosts(PageRequest.of(0, 5), PostSearchRequestDto(title = "title1"))
            then("타이틀에 해당하는 게시글이 반환된다.") {
                all.number shouldBe 0
                all.size shouldBe 5
                all.content.size shouldBe 2
                all.content[0].title shouldContain "title1"
                all.content[0].createdBy shouldContain "cherhy"
            }
        }

        When("작성자로 검색") {
            val all = postService.getPosts(PageRequest.of(0, 5), PostSearchRequestDto(createdBy = "cherhy2"))
            then("타이틀에 해당하는 게시글이 반환된다.") {
                all.number shouldBe 0
                all.size shouldBe 5
                all.content.size shouldBe 5
                all.content[0].title shouldContain "title"
                all.content[0].createdBy shouldContain "cherhy2"
            }

            then("첫번째 태그가 함께 조회됨을 확인한다.") {
                all.content.forEach {
                    it.firstTag shouldBe "tag1"
                }
            }
        }

        When("태그로 검색") {
            val all = postService.getPosts(PageRequest.of(0, 5), PostSearchRequestDto(tag = "tag1"))

            then("태그에 해당하는 게시글이 반환된다.") {
                all.number shouldBe 0
                all.size shouldBe 0
                all.content.size shouldBe 0
                all.content[0].title shouldBe 0
            }
        }

        When("좋아요가 2개 추가되었을 때") {
            val all = postService.getPosts(PageRequest.of(0, 5), PostSearchRequestDto(tag = "tag1"))

            all.content.forEach {
                likeService.createLike(it.id, "cherhy")
                likeService.createLike(it.id, "onehy")
            }

            val withLike = postService.getPosts(PageRequest.of(0, 5), PostSearchRequestDto(tag = "tag1"))

            then("좋아요 개수가 정상적으로 조회됨을 확인한다.") {
                withLike.content.forEach {
                    it.likeCount shouldBe 2
                }
            }
        }
    }
})
