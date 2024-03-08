package ru.pgk.pgk.features.schedule.dto.teacher;

public record ScheduleTeacherColumnDto(
        Integer number,
        String shift,
        String group_name,
        String cabinet
) {}
