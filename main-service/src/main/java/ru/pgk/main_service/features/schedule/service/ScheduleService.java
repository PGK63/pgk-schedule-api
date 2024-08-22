package ru.pgk.main_service.features.schedule.service;

import org.springframework.data.domain.Page;
import ru.pgk.main_service.features.schedule.dto.params.GetScheduleType;
import ru.pgk.main_service.features.schedule.dto.student.ScheduleStudentResponse;
import ru.pgk.main_service.features.schedule.dto.teacher.ScheduleTeacherResponse;
import ru.pgk.main_service.features.schedule.entities.ScheduleEntity;
import ru.pgk.main_service.features.schedule.dto.script.ScheduleDto;
import ru.pgk.main_service.features.schedule.dto.script.ScheduleRowDto;
import ru.pgk.main_service.features.student.entities.StudentEntity;
import ru.pgk.main_service.features.teacher.entities.TeacherEntity;

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

    ScheduleEntity add(ScheduleDto schedule, Short departmentId);

    ScheduleEntity updateRowsByDepartmentAndDate(Short departmentId, LocalDate date, List<ScheduleRowDto> rows);
}
