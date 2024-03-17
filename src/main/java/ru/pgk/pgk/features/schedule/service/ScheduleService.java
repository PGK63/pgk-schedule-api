package ru.pgk.pgk.features.schedule.service;

import ru.pgk.pgk.features.schedule.dto.student.ScheduleStudentResponse;
import ru.pgk.pgk.features.schedule.dto.teacher.ScheduleTeacherResponse;
import ru.pgk.pgk.features.schedule.entities.ScheduleEntity;
import ru.pgk.pgk.features.schedule.entities.json.Schedule;
import ru.pgk.pgk.features.schedule.entities.json.ScheduleRow;
import ru.pgk.pgk.features.student.entities.StudentEntity;
import ru.pgk.pgk.features.teacher.entities.TeacherEntity;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleService {

    List<ScheduleEntity> getAll(Short departmentId);

    ScheduleStudentResponse studentGetById(Integer scheduleId, Long telegramId);
    ScheduleStudentResponse getByStudent(Integer scheduleId, StudentEntity student);

    ScheduleTeacherResponse teacherGetById(Integer scheduleId, Long telegramId);
    ScheduleTeacherResponse getByTeacher(Integer scheduleId, TeacherEntity teacher);

    ScheduleEntity add(Schedule schedule, Short departmentId);

    ScheduleEntity updateRowsByDepartmentAndDate(Short departmentId, LocalDate date, List<ScheduleRow> rows);
}
