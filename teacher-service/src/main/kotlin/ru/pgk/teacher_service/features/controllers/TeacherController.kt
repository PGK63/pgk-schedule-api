package ru.pgk.teacher_service.features.controllers

import org.springframework.web.bind.annotation.*
import ru.pgk.teacher_service.common.dto.PageDto
import ru.pgk.teacher_service.common.dto.toDto
import ru.pgk.teacher_service.features.dto.TeacherDetailsDto
import ru.pgk.teacher_service.features.dto.params.AddOrUpdateTeacherParams
import ru.pgk.teacher_service.features.mappers.TeacherDetailsMapper
import ru.pgk.teacher_service.features.services.TeacherService
import ru.pgk.teacher_service.features.services.queries.TeacherQueriesService

@RestController
class TeacherController(
    private val teacherQueriesService: TeacherQueriesService,
    private val teacherService: TeacherService,

    private val teacherDetailsMapper: TeacherDetailsMapper
) {

    @GetMapping
    private fun getAll(
        @RequestParam(required = false) name: String?,
        @RequestParam(defaultValue = "0") offset: Int
    ): PageDto<TeacherDetailsDto> {
        val teachers = teacherQueriesService.getAll(name, offset).map(teacherDetailsMapper::toDto)
        return teachers.toDto()
    }

    @PostMapping
    private fun add(
        @RequestBody params: AddOrUpdateTeacherParams,
    ): TeacherDetailsDto {
        return teacherDetailsMapper.toDto(teacherService.add(params))
    }

    @PutMapping("{id}")
    private fun update(
        @PathVariable id: Int,
        @RequestBody params: AddOrUpdateTeacherParams
    ): TeacherDetailsDto {
        return teacherDetailsMapper.toDto(teacherService.update(id, params))
    }

    @DeleteMapping("{id}")
    private fun deleteById(
        @PathVariable id: Int,
    ) {
        teacherService.deleteById(id)
    }
}