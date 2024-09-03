package ru.pgk.teacher_service.common.dto

import org.springframework.data.domain.Page

data class PageDto<T>(
    val content: List<T>,
    val totalPage: Int,
    val last: Boolean ,
    val first: Boolean
)

fun <T>Page<T>.toDto(): PageDto<T> {
    return PageDto(this.content, this.totalPages, this.isLast, this.isFirst)
}