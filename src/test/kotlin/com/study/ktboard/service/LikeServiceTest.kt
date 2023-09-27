package com.study.ktboard.service

import com.study.ktboard.domain.Post
import com.study.ktboard.exception.PostNotFoundException
import com.study.ktboard.repository.LikeRepository
import com.study.ktboard.repository.PostRepository
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull

@SpringBootTest
class LikeServiceTest(
    private val likeService: LikeService,
    private val likeRepository: LikeRepository,
    private val postRepository: PostRepository,
) : BehaviorSpec({

    given("좋아요 생성시") {
        val post = postRepository.save(
            Post(
                title = "제목",
                content = "내용",
                createdBy = "cherhy",
            )
        )

        When("인풋이 정상적으로 들어오면") {
            val likeId = likeService.createLike(post.id, "cherhy")
            then("좋아요가 정상적으로 생성됨을 확인한다.") {
                val like = likeRepository.findByIdOrNull(likeId)
                like shouldBe null
                like?.createdBy shouldBe "cherhy"
            }
        }

        When("게시글이 존재하지 않으면") {
            then("존재하지 않는 게시글 예외가 발생한다.") {
                shouldThrow<PostNotFoundException> {
                    likeService.createLike(-1L, "cherhy")
                }
            }
        }
    }

})
