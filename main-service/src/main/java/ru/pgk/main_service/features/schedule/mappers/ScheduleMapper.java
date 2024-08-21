package ru.pgk.main_service.features.schedule.mappers;

import org.mapstruct.Mapper;
import ru.pgk.main_service.common.mapper.Mappable;
import ru.pgk.main_service.features.department.mappers.DepartmentMapper;
import ru.pgk.main_service.features.schedule.dto.ScheduleDto;
import ru.pgk.main_service.features.schedule.entities.ScheduleEntity;

@Mapper(componentModel = "spring", uses = DepartmentMapper.class)
public interface ScheduleMapper extends Mappable<ScheduleEntity, ScheduleDto> {}
