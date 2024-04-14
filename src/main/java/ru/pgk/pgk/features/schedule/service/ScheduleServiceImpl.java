package ru.pgk.pgk.features.schedule.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pgk.pgk.common.exceptions.ResourceNotFoundException;
import ru.pgk.pgk.features.department.entitites.DepartmentEntity;
import ru.pgk.pgk.features.department.services.DepartmentService;
import ru.pgk.pgk.features.schedule.dto.student.ScheduleStudentResponse;
import ru.pgk.pgk.features.schedule.dto.teacher.ScheduleTeacherColumnDto;
import ru.pgk.pgk.features.schedule.dto.teacher.ScheduleTeacherResponse;
import ru.pgk.pgk.features.schedule.dto.teacher.ScheduleTeacherRowDto;
import ru.pgk.pgk.features.schedule.entities.ScheduleEntity;
import ru.pgk.pgk.features.schedule.entities.json.Schedule;
import ru.pgk.pgk.features.schedule.entities.json.ScheduleColumn;
import ru.pgk.pgk.features.schedule.entities.json.ScheduleRow;
import ru.pgk.pgk.features.schedule.repositories.ScheduleRepository;
import ru.pgk.pgk.features.student.entities.StudentEntity;
import ru.pgk.pgk.features.student.services.StudentService;
import ru.pgk.pgk.features.teacher.entities.TeacherEntity;
import ru.pgk.pgk.features.teacher.entities.TeacherUserEntity;
import ru.pgk.pgk.features.teacher.service.queries.TeacherQueriesService;
import ru.pgk.pgk.features.teacher.service.user.TeacherUserService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;

    private final StudentService studentService;
    private final TeacherUserService teacherUserService;
    private final TeacherQueriesService teacherQueriesService;
    private final DepartmentService departmentService;

    @Value("${schedule.page_size}")
    private Integer schedulePageSize;

    @Override
    @Transactional(readOnly = true)
    @Caching(
            cacheable = {
                    @Cacheable(
                            cacheNames = "ScheduleService::getAllByDepartmentIdAndOffset",
                            condition = "#departmentIds != null",
                            key = "#departmentIds.toString() + '-' + #offset"
                    ),
                    @Cacheable(
                            cacheNames = "ScheduleService::getAllByDepartmentIdAndOffset",
                            condition = "#departmentIds == null",
                            key = "'0-' + #offset"
                    )
            }
    )
    public Page<ScheduleEntity> getAll(List<Short> departmentIds, Integer offset) {
        PageRequest pageRequest = PageRequest.of(offset, schedulePageSize);

        if(departmentIds != null)
            return scheduleRepository.findAllByDepartmentIdsOrderByDateDesc(departmentIds, pageRequest);

        return scheduleRepository.findAllDateDesc(pageRequest);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "ScheduleService::studentGetById", key = "#scheduleId-#telegramId")
    public ScheduleStudentResponse studentGetByTelegramId(Integer scheduleId, Long telegramId) {
        StudentEntity student = studentService.getByTelegramId(telegramId);
        return getByStudent(scheduleId, student);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "ScheduleService::getByStudent", key = "#scheduleId-#student.id")
    public ScheduleStudentResponse getByStudent(Integer scheduleId, StudentEntity student) {
        return studentGetByGroupName(scheduleId, student.getGroup().getName());
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "ScheduleService::studentGetByGroupName", key = "#scheduleId + '-' + #groupName")
    public ScheduleStudentResponse studentGetByGroupName(Integer scheduleId, String groupName) {
        ScheduleEntity schedule = getById(scheduleId);
        Optional<ScheduleRow> optionalRow = schedule.getRows().stream()
                .filter(r -> r.group_name().contains(groupName)).findFirst();

        if(optionalRow.isEmpty()) throw new ResourceNotFoundException("Schedule not found");
        ScheduleRow row = optionalRow.get();

        return new ScheduleStudentResponse(
                schedule.getDate(),
                row.shift(),
                row.columns().stream().peek(r -> {
                    if(r.getTeacher() == null) {
                        try {
                            TeacherEntity teacher = teacherQueriesService.getByCabinet(r.getCabinet());
                            r.setTeacher(teacher.getFIO());
                        }catch (ResourceNotFoundException ignore) {}
                    }
                }).toList()
        );
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "ScheduleService::teacherGetById", key = "#scheduleId-#telegramId")
    public ScheduleTeacherResponse teacherGetByTelegramId(Integer scheduleId, Long telegramId) {
        TeacherUserEntity teacher = teacherUserService.getByTelegramId(telegramId);
        return getByTeacher(scheduleId, teacher.getTeacher());
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "ScheduleService::teacherGetByTeacherId", key = "#scheduleId-#teacherId")
    public ScheduleTeacherResponse teacherGetByTeacherId(Integer scheduleId, Integer teacherId) {
        TeacherEntity teacher = teacherQueriesService.getById(teacherId);
        return getByTeacher(scheduleId, teacher);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "ScheduleService::getByTeacher", key = "#scheduleId-#teacher.id")
    public ScheduleTeacherResponse getByTeacher(Integer scheduleId, TeacherEntity teacher) {
        ScheduleEntity schedule = getById(scheduleId);
        List<ScheduleTeacherRowDto> teacherRows = new ArrayList<>();

        for (ScheduleRow row : schedule.getRows()) {
            List<ScheduleTeacherColumnDto> teacherColumns = getScheduleTeacherColumnDtos(teacher, row);

            if (!teacherColumns.isEmpty()) {
                ScheduleTeacherRowDto teacherRow = new ScheduleTeacherRowDto(row.shift(), row.group_name(), teacherColumns);
                teacherRows.add(teacherRow);
            }
        }

        List<ScheduleTeacherRowDto> sortedTeacherRows = teacherRows.stream()
                .sorted(Comparator.comparing((ScheduleTeacherRowDto teacherRow) -> {
                    String shift = teacherRow.shift()
                            .toLowerCase()
                            .replace("с ", "")
                            .trim();
                    shift = shift.equals("1 см") ? "8:30" : shift.equals("2 см") ? "13:30" : shift;
                    String[] parts = shift.split(":");
                    return Integer.parseInt(parts[0]) * 60 + Integer.parseInt(parts[1]);
                }).thenComparing(row -> row.columns().stream().map(ScheduleTeacherColumnDto::number).min(Integer::compareTo).orElse(Integer.MAX_VALUE)))
                .toList();

        return new ScheduleTeacherResponse(
                schedule.getDate(),
                sortedTeacherRows
        );
    }

    private static List<ScheduleTeacherColumnDto> getScheduleTeacherColumnDtos(TeacherEntity teacher, ScheduleRow row) {
        List<ScheduleTeacherColumnDto> teacherColumns = new ArrayList<>();

        for (ScheduleColumn column : row.columns()) {
            if((column.getTeacher() == null && teacher.getCabinet() != null && column.getCabinet() != null
                    && column.getCabinet().contains(teacher.getCabinet()))
                    || (column.getTeacher() != null
                    && column.getTeacher().equals(teacher.getFI()))
            ) {
                ScheduleTeacherColumnDto teacherColumn = new ScheduleTeacherColumnDto(
                        column.getNumber(),
                        column.getCabinet(),
                        column.getExam()
                );
                teacherColumns.add(teacherColumn);
            }
        }
        return teacherColumns;
    }

    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "ScheduleService::getById", key = "#id")
    public ScheduleEntity getById(Integer id) {
        return scheduleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Schedule not found"));
    }

    @Override
    @Transactional
    @CachePut(cacheNames = "ScheduleService::getById", key = "#result.id")
    public ScheduleEntity add(Schedule schedule, Short departmentId) {
        DepartmentEntity department = departmentService.getById(departmentId);
        ScheduleEntity scheduleEntity = new ScheduleEntity();
        scheduleEntity.setDate(schedule.date());
        scheduleEntity.setRows(schedule.rows());
        scheduleEntity.setDepartment(department);
        return scheduleRepository.save(scheduleEntity);
    }

    @Override
    @Transactional(readOnly = true)
    @CachePut(cacheNames = "ScheduleService::getById", key = "#result.id")
    public ScheduleEntity updateRowsByDepartmentAndDate(
            Short departmentId,
            LocalDate date,
            List<ScheduleRow> rows
    ) {
        ScheduleEntity schedule = scheduleRepository.findByDepartmentIdAndDate(departmentId, date)
                .orElseThrow(() -> new ResourceNotFoundException("Schedule not found"));
        schedule.setRows(rows);
        return scheduleRepository.save(schedule);
    }
}
