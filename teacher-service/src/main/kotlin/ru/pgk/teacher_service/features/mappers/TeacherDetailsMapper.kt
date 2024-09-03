package ru.pgk.teacher_service.features.mappers

import org.mapstruct.Mapper
import ru.pgk.teacher_service.common.mapper.Mappable
import ru.pgk.teacher_service.features.dto.TeacherDetailsDto
import ru.pgk.teacher_service.features.entities.TeacherEntity

@Mapper(componentModel = "spring")
interface TeacherDetailsMapper: Mappable<TeacherEntity, TeacherDetailsDto>