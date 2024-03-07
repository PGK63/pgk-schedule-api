package ru.pgk.pgk.features.schedule.dto;

import java.util.List;

public record ScheduleRowDto(
        String group_name,
        String shift,
        List<ScheduleColumnDto> columns
) {}
