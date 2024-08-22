package ru.pgk.main_service.features.schedule.mappers.grpc.script;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.pgk.main_service.common.mapper.StringMapper;
import ru.pgk.main_service.features.schedule.dto.script.ScheduleColumnDto;
import ru.pgk.schedule_service.lib.ScheduleColumnReply;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring", uses = StringMapper.class)
public interface ScheduleColumnReplyMapper {
    ScheduleColumnReplyMapper INSTANCE = Mappers.getMapper(ScheduleColumnReplyMapper.class);

    @Mapping(source = "reply.teacher", target = "teacher", qualifiedByName = "emptyStringToNull")
    @Mapping(source = "reply.cabinet", target = "cabinet", qualifiedByName = "emptyStringToNull")
    ScheduleColumnDto toDto(ScheduleColumnReply reply);

    List<ScheduleColumnDto> toDto(Collection<ScheduleColumnReply> replies);
}
