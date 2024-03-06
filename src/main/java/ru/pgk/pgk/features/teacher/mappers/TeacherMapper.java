package ru.pgk.pgk.features.teacher.mappers;

import org.mapstruct.Mapper;
import ru.pgk.pgk.common.mapper.Mappable;
import ru.pgk.pgk.features.department.mappers.DepartmentMapper;
import ru.pgk.pgk.features.teacher.dto.TeacherDto;
import ru.pgk.pgk.features.teacher.entities.TeacherEntity;

@Mapper(componentModel = "spring", uses = DepartmentMapper.class)
public interface TeacherMapper extends Mappable<TeacherEntity, TeacherDto> {}
