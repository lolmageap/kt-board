package com.study.ktboard.exception

open class PostException(message: String) : RuntimeException()

class PostNotFoundException() : PostException("게시글을 찾을 수 없습니다.")
class PostNotUpdatableException() : PostException("수정할 수 없는 게시물입니다..")