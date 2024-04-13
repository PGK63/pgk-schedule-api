package ru.pgk.pgk.features.group.mappers;

import org.mapstruct.Mapper;
import ru.pgk.pgk.common.mapper.Mappable;
import ru.pgk.pgk.features.group.dto.GroupDto;
import ru.pgk.pgk.features.group.entities.GroupEntity;

@Mapper(componentModel = "spring")
public interface GroupMapper extends Mappable<GroupEntity, GroupDto> {}
