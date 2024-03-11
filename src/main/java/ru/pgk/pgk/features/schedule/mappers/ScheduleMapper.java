package ru.pgk.pgk.features.schedule.mappers;

import org.mapstruct.Mapper;
import ru.pgk.pgk.common.mapper.Mappable;
import ru.pgk.pgk.features.schedule.dto.ScheduleDto;
import ru.pgk.pgk.features.schedule.entities.ScheduleEntity;

@Mapper(componentModel = "spring")
public interface ScheduleMapper extends Mappable<ScheduleEntity, ScheduleDto> {}
