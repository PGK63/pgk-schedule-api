package ru.pgk.pgk.features.schedule.dto.teacher;

import java.io.Serializable;
import java.util.List;

public record ScheduleTeacherRowDto(
        String shift,
        String group_name,
        List<ScheduleTeacherColumnDto> columns
) implements Serializable {}
