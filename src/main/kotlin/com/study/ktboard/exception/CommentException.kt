package com.study.ktboard.exception

open class CommentException(override val message: String?) : RuntimeException()
class CommentNotFoundException() : CommentException("댓글이 존재하지 않습니다.")
class CommentNotUpdatableException() : CommentException("수정할 수 없는 댓글입니다.")
class CommentNotDeletableException() : CommentException("삭제할 수 없는 댓글입니다.")
