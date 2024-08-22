package ru.pgk.main_service.features.schedule.dto.script;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public record ScheduleDto(
        LocalDate date,
        List<ScheduleRowDto> rows
) implements Serializable {}
