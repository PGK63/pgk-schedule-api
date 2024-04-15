package ru.pgk.pgk.features.secretKey.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.pgk.pgk.common.mapper.Mappable;
import ru.pgk.pgk.features.secretKey.dto.SecretKeyDto;
import ru.pgk.pgk.features.secretKey.entities.SecretKeyEntity;

@Mapper(componentModel = "spring")
public interface SecretKeyMapper extends Mappable<SecretKeyEntity, SecretKeyDto> {

    @Override
    @Mapping(source = "entity.id.type.value", target = "type")
    SecretKeyDto toDto(SecretKeyEntity entity);
}
