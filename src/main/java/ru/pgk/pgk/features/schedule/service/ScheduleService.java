package ru.pgk.pgk.features.schedule.service;

import org.springframework.data.domain.Page;
import ru.pgk.pgk.features.schedule.dto.params.GetScheduleType;
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

    Page<ScheduleEntity> getAll(List<Short> departmentIds, Integer offset);
    ScheduleEntity getByType(GetScheduleType type, List<Short> departmentIds);

    ScheduleStudentResponse studentGetByTelegramId(Integer scheduleId, Long telegramId);
    ScheduleStudentResponse studentGetByAliceId(GetScheduleType type, String aliceId);
    ScheduleStudentResponse getByStudent(Integer scheduleId, StudentEntity student);
    ScheduleStudentResponse studentGetByGroupName(Integer scheduleId, String groupName);

    ScheduleTeacherResponse teacherGetByTelegramId(Integer scheduleId, Long telegramId);
    ScheduleTeacherResponse teacherGetByAliceId(GetScheduleType type, String aliceId);
    ScheduleTeacherResponse teacherGetByTeacherId(Integer scheduleId, Integer teacherId);
    ScheduleTeacherResponse getByTeacher(Integer scheduleId, TeacherEntity teacher);

    ScheduleEntity add(Schedule schedule, Short departmentId);

    ScheduleEntity updateRowsByDepartmentAndDate(Short departmentId, LocalDate date, List<ScheduleRow> rows);
}
