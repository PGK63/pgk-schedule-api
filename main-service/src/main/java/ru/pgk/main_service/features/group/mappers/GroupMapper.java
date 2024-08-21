package ru.pgk.main_service.features.group.mappers;

import org.mapstruct.Mapper;
import ru.pgk.main_service.common.mapper.Mappable;
import ru.pgk.main_service.features.group.dto.GroupDto;
import ru.pgk.main_service.features.group.entities.GroupEntity;

@Mapper(componentModel = "spring")
public interface GroupMapper extends Mappable<GroupEntity, GroupDto> {}
