package ru.pgk.pgk.features.schedule.mappers;

import org.mapstruct.Mapper;
import ru.pgk.pgk.common.mapper.Mappable;
import ru.pgk.pgk.features.department.mappers.DepartmentMapper;
import ru.pgk.pgk.features.schedule.dto.ScheduleDto;
import ru.pgk.pgk.features.schedule.entities.ScheduleEntity;

@Mapper(componentModel = "spring", uses = DepartmentMapper.class)
public interface ScheduleMapper extends Mappable<ScheduleEntity, ScheduleDto> {}
