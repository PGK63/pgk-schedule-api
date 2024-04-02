package ru.pgk.pgk.features.apiToken.mappers;

import org.mapstruct.Mapper;
import ru.pgk.pgk.common.mapper.Mappable;
import ru.pgk.pgk.features.apiToken.dto.ApiTokenDto;
import ru.pgk.pgk.features.apiToken.entities.ApiTokenEntity;

@Mapper(componentModel = "spring")
public interface ApiTokenMapper extends Mappable<ApiTokenEntity, ApiTokenDto> {}
