package ru.pgk.main_service.features.schedule.mappers.grpc.script;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import ru.pgk.main_service.features.schedule.dto.script.ScheduleColumnDto;
import ru.pgk.main_service.features.schedule.dto.script.ScheduleRowDto;
import ru.pgk.schedule_service.lib.ScheduleColumnReply;
import ru.pgk.schedule_service.lib.ScheduleRowReply;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring", uses = {ScheduleColumnReplyMapper.class})
public interface ScheduleRowReplyMapper {

    ScheduleRowReplyMapper INSTANCE = Mappers.getMapper(ScheduleRowReplyMapper.class);

    @Mapping(source = "reply.columnsList", target = "columns", qualifiedByName = "toColumnsDto")
    ScheduleRowDto toDto(ScheduleRowReply reply);

    List<ScheduleRowDto> toDto(Collection<ScheduleRowReply> replies);

    @Named("toColumnsDto")
    default List<ScheduleColumnDto> toColumnsDto(List<ScheduleColumnReply> columns) {
        return ScheduleColumnReplyMapper.INSTANCE.toDto(columns);
    }
}
