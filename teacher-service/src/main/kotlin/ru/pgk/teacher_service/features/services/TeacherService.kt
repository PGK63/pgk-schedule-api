package ru.pgk.teacher_service.features.services

import ru.pgk.teacher_service.features.dto.params.AddOrUpdateTeacherParams
import ru.pgk.teacher_service.features.entities.TeacherEntity

interface TeacherService {
    fun add(params: AddOrUpdateTeacherParams): TeacherEntity
    fun update(id: Int, params: AddOrUpdateTeacherParams): TeacherEntity
    fun deleteById(id: Int): TeacherEntity
}