package ru.pgk.main_service.features.teacher.mappers;

import org.mapstruct.Mapper;
import ru.pgk.main_service.common.mapper.Mappable;
import ru.pgk.main_service.features.department.mappers.DepartmentMapper;
import ru.pgk.main_service.features.teacher.dto.TeacherDto;
import ru.pgk.main_service.features.teacher.entities.TeacherEntity;

@Mapper(componentModel = "spring", uses = DepartmentMapper.class)
public interface TeacherMapper extends Mappable<TeacherEntity, TeacherDto> {}
