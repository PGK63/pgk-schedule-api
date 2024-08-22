package ru.pgk.main_service.features.schedule.mappers.grpc.script;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.pgk.main_service.features.schedule.dto.script.ScheduleColumnDto;
import ru.pgk.schedule_service.lib.ScheduleColumnReply;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ScheduleColumnReplyMapper {
    ScheduleColumnReplyMapper INSTANCE = Mappers.getMapper(ScheduleColumnReplyMapper.class);

    ScheduleColumnDto toDto(ScheduleColumnReply reply);

    List<ScheduleColumnDto> toDto(Collection<ScheduleColumnReply> replies);
}
