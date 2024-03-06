package ru.pgk.pgk.features.department.mappers;

import org.mapstruct.Mapper;
import ru.pgk.pgk.common.mapper.Mappable;
import ru.pgk.pgk.features.department.dto.DepartmentDto;
import ru.pgk.pgk.features.department.entitites.DepartmentEntity;

@Mapper(componentModel = "spring")
public interface DepartmentMapper extends Mappable<DepartmentEntity, DepartmentDto> {}
