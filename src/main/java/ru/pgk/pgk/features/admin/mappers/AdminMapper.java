package ru.pgk.pgk.features.admin.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.pgk.pgk.common.mapper.Mappable;
import ru.pgk.pgk.features.admin.dto.AdminDto;
import ru.pgk.pgk.features.admin.entities.AdminEntity;

@Mapper(componentModel = "spring")
public interface AdminMapper extends Mappable<AdminEntity, AdminDto> {

    @Override
    @Mapping(source = "entity.userId", target = "id")
    AdminDto toDto(AdminEntity entity);
}
