package ru.pgk.pgk.features.schedule.dto.teacher;

import java.io.Serializable;

public record ScheduleTeacherColumnDto(
        Integer number,
        String shift,
        String group_name,
        String cabinet,
        Boolean exam
) implements Serializable {}
