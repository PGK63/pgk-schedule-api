package ru.pgk.pgk.features.schedule.service;

import ru.pgk.pgk.features.schedule.dto.student.ScheduleStudentResponse;
import ru.pgk.pgk.features.schedule.dto.teacher.ScheduleTeacherResponse;
import ru.pgk.pgk.features.schedule.entities.ScheduleEntity;

import java.util.List;

public interface ScheduleService {

    List<ScheduleEntity> getAll(Short departmentId);

    ScheduleStudentResponse studentGetById(Integer scheduleId, Long telegramId);

    ScheduleTeacherResponse teacherGetById(Integer scheduleId, Long telegramId);
}
