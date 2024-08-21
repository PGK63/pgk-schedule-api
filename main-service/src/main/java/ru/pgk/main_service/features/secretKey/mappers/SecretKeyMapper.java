package ru.pgk.main_service.features.secretKey.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.pgk.main_service.common.mapper.Mappable;
import ru.pgk.main_service.features.secretKey.dto.SecretKeyDto;
import ru.pgk.main_service.features.secretKey.entities.SecretKeyEntity;

@Mapper(componentModel = "spring")
public interface SecretKeyMapper extends Mappable<SecretKeyEntity, SecretKeyDto> {

    @Override
    @Mapping(source = "entity.id.type.value", target = "type")
    SecretKeyDto toDto(SecretKeyEntity entity);
}
