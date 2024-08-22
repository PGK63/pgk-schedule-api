package ru.pgk.main_service.features.schedule.mappers.grpc.script;

import com.google.protobuf.Timestamp;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.pgk.main_service.common.converters.GrpcTimestampConverter;
import ru.pgk.main_service.features.schedule.dto.script.ScheduleDto;
import ru.pgk.main_service.features.schedule.dto.script.ScheduleRowDto;
import ru.pgk.schedule_service.lib.ScheduleReply;
import ru.pgk.schedule_service.lib.ScheduleRowReply;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ScheduleReplyMapper {

    @Mapping(source = "reply.rowsList", target = "rows", qualifiedByName = "toRowsDto")
    @Mapping(source = "reply.date", target = "date", qualifiedByName = "timestampToLocalDate")
    ScheduleDto toDto(ScheduleReply reply);

    List<ScheduleDto> toDto(Collection<ScheduleReply> replies);

    @Named("timestampToLocalDate")
    default LocalDate timestampToLocalDate(Timestamp timestamp) {
        return GrpcTimestampConverter.timestampToLocalDate(timestamp);
    }

    @Named("toRowsDto")
    default List<ScheduleRowDto> toRowsDto(List<ScheduleRowReply> rows) {
        return ScheduleRowReplyMapper.INSTANCE.toDto(rows);
    }
}
