package ru.pgk.teacher_service.features.services.queries

import org.springframework.data.domain.Page
import ru.pgk.teacher_service.features.entities.TeacherEntity

interface TeacherQueriesService {
    fun getAll(name: String?, offset: Int): Page<TeacherEntity>
    fun getById(id: Int): TeacherEntity
    fun getByCabinet(cabinet: String): TeacherEntity
}