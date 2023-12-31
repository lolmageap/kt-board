package com.study.ktboard

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableAsync

@EnableAsync
@SpringBootApplication
class KtBoardApplication

fun main(args: Array<String>) {
    runApplication<KtBoardApplication>(*args)
}
