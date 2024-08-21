package ru.pgk.main_service.features.department.mappers;

import org.mapstruct.Mapper;
import ru.pgk.main_service.common.mapper.Mappable;
import ru.pgk.main_service.features.department.dto.DepartmentDto;
import ru.pgk.main_service.features.department.entitites.DepartmentEntity;

@Mapper(componentModel = "spring")
public interface DepartmentMapper extends Mappable<DepartmentEntity, DepartmentDto> {}
