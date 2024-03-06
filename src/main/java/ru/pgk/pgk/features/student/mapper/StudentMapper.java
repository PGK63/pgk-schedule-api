package ru.pgk.pgk.features.student.mapper;

import org.mapstruct.Mapper;
import ru.pgk.pgk.common.mapper.Mappable;
import ru.pgk.pgk.features.student.dto.StudentDto;
import ru.pgk.pgk.features.student.entities.StudentEntity;

@Mapper(componentModel = "spring")
public interface StudentMapper extends Mappable<StudentEntity, StudentDto> {}
