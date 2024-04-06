package ru.pgk.pgk.features.schedule.service;

import org.springframework.data.domain.Page;
import ru.pgk.pgk.features.schedule.dto.student.ScheduleStudentResponse;
import ru.pgk.pgk.features.schedule.dto.teacher.ScheduleTeacherResponse;
import ru.pgk.pgk.features.schedule.entities.ScheduleEntity;
import ru.pgk.pgk.features.schedule.entities.json.Schedule;
import ru.pgk.pgk.features.schedule.entities.json.ScheduleRow;
import ru.pgk.pgk.features.student.entities.StudentEntity;
import ru.pgk.pgk.features.teacher.entities.TeacherUserEntity;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleService {

    Page<ScheduleEntity> getAll(Short departmentId, Integer offset);

    ScheduleStudentResponse studentGetById(Integer scheduleId, Long telegramId);
    ScheduleStudentResponse getByStudent(Integer scheduleId, StudentEntity student);

    ScheduleTeacherResponse teacherGetById(Integer scheduleId, Long telegramId);
    ScheduleTeacherResponse getByTeacher(Integer scheduleId, TeacherUserEntity teacher);

    ScheduleEntity add(Schedule schedule, Short departmentId);

    ScheduleEntity updateRowsByDepartmentAndDate(Short departmentId, LocalDate date, List<ScheduleRow> rows);
}
