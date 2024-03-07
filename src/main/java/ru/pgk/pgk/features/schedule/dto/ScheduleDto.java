package ru.pgk.pgk.features.schedule.dto;

import java.util.List;

public record ScheduleDto(
        String date,
        List<ScheduleRowDto> rows
) {}
