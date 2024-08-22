package ru.pgk.main_service.features.schedule.dto.script;

import java.io.Serializable;
import java.util.List;

public record ScheduleRowDto(
        String group_name,
        String shift,
        List<ScheduleColumnDto> columns
) implements Serializable {}
