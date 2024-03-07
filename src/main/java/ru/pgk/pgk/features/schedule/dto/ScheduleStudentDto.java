package ru.pgk.pgk.features.schedule.dto;

public record ScheduleStudentDto(
   String group,
   String shift,
   Integer number,
   String teacher,
   String cabinet
) {}
