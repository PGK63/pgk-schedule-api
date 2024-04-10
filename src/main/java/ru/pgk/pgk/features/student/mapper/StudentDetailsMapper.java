package ru.pgk.pgk.features.student.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.pgk.pgk.common.mapper.Mappable;
import ru.pgk.pgk.features.department.mappers.DepartmentMapper;
import ru.pgk.pgk.features.student.dto.StudentDetailsDto;
import ru.pgk.pgk.features.student.entities.StudentEntity;

@Mapper(componentModel = "spring", uses = {DepartmentMapper.class})
public interface StudentDetailsMapper extends Mappable<StudentEntity, StudentDetailsDto> {

    @Override
    @Mapping(source = "entity.group.name", target = "groupName")
    @Mapping(source = "entity.group.department", target = "department")
    StudentDetailsDto toDto(StudentEntity entity);
}
