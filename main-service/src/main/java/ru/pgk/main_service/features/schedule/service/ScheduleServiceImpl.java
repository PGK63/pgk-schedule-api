package ru.pgk.main_service.features.schedule.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pgk.main_service.common.exceptions.ResourceNotFoundException;
import ru.pgk.main_service.features.department.entitites.DepartmentEntity;
import ru.pgk.main_service.features.department.services.DepartmentService;
import ru.pgk.main_service.features.schedule.dto.params.GetScheduleType;
import ru.pgk.main_service.features.schedule.dto.student.ScheduleStudentResponse;
import ru.pgk.main_service.features.schedule.dto.teacher.ScheduleTeacherColumnDto;
import ru.pgk.main_service.features.schedule.dto.teacher.ScheduleTeacherResponse;
import ru.pgk.main_service.features.schedule.dto.teacher.ScheduleTeacherRowDto;
import ru.pgk.main_service.features.schedule.entities.ScheduleEntity;
import ru.pgk.main_service.features.schedule.dto.script.ScheduleDto;
import ru.pgk.main_service.features.schedule.dto.script.ScheduleColumnDto;
import ru.pgk.main_service.features.schedule.dto.script.ScheduleRowDto;
import ru.pgk.main_service.features.schedule.repositories.ScheduleRepository;
import ru.pgk.main_service.features.student.entities.StudentEntity;
import ru.pgk.main_service.features.student.services.StudentService;
import ru.pgk.main_service.features.teacher.entities.TeacherEntity;
import ru.pgk.main_service.features.teacher.entities.TeacherUserEntity;
import ru.pgk.main_service.features.teacher.service.queries.TeacherQueriesService;
import ru.pgk.main_service.features.teacher.service.user.TeacherUserService;

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
    public ScheduleEntity getByType(GetScheduleType type, List<Short> departmentIds) {
        LocalDate date = LocalDate.now();
        Optional<ScheduleEntity> schedule;

        if(type == GetScheduleType.TODAY) {
            schedule = scheduleRepository.findByDateAndDepartmentIds(date, departmentIds);
        }else if(type == GetScheduleType.NEXT_DAY){
            schedule = scheduleRepository.findByNextDateAndDepartmentIds(date, departmentIds);
        }else {
            schedule = scheduleRepository.findByLastDate(departmentIds);
        }

        return schedule.orElseThrow(() -> new ResourceNotFoundException("Schedule not found"));
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "ScheduleService::studentGetByTelegramId", key = "#scheduleId + '-' + #telegramId")
    public ScheduleStudentResponse studentGetByTelegramId(Integer scheduleId, Long telegramId) {
        StudentEntity student = studentService.getByTelegramId(telegramId);
        return getByStudent(scheduleId, student);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "ScheduleService::studentGetByAliceId", key = "#aliceId + '-' + #type")
    public ScheduleStudentResponse studentGetByAliceId(GetScheduleType type, String aliceId) {
        StudentEntity student = studentService.getByAliceId(aliceId);
        List<Short> departmentIds = new ArrayList<>();
        departmentIds.add(student.getGroup().getDepartment().getId());
        ScheduleEntity schedule = getByType(type, departmentIds);
        return getByStudent(schedule.getId(), student);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "ScheduleService::getByStudent", key = "#scheduleId + '-' + #student.id")
    public ScheduleStudentResponse getByStudent(Integer scheduleId, StudentEntity student) {
        return studentGetByGroupName(scheduleId, student.getGroup().getName());
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "ScheduleService::studentGetByGroupName", key = "#scheduleId + '-' + #groupName")
    public ScheduleStudentResponse studentGetByGroupName(Integer scheduleId, String groupName) {
        ScheduleEntity schedule = getById(scheduleId);
        Optional<ScheduleRowDto> optionalRow = schedule.getRows().stream()
                .filter(r -> r.groupName().contains(groupName)).findFirst();

        if(optionalRow.isEmpty()) throw new ResourceNotFoundException("Schedule not found");
        ScheduleRowDto row = optionalRow.get();

        return new ScheduleStudentResponse(
                schedule.getDate(),
                row.shift(),
                row.columns()
        );
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "ScheduleService::teacherGetByTelegramId", key = "#scheduleId + '-' + #telegramId")
    public ScheduleTeacherResponse teacherGetByTelegramId(Integer scheduleId, Long telegramId) {
        TeacherUserEntity teacher = teacherUserService.getByTelegramId(telegramId);
        return getByTeacher(scheduleId, teacher.getTeacher());
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "ScheduleService::teacherGetByAliceId", key = "#aliceId + '-' + #type")
    public ScheduleTeacherResponse teacherGetByAliceId(GetScheduleType type, String aliceId) {
        TeacherUserEntity teacher = teacherUserService.getByAliceId(aliceId);
        List<Short> departmentIds = teacher.getTeacher().getDepartments().stream().map(DepartmentEntity::getId).toList();
        ScheduleEntity schedule = getByType(type, departmentIds);
        return getByTeacher(schedule.getId(), teacher.getTeacher());
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "ScheduleService::teacherGetByTeacherId", key = "#scheduleId + '-' + #teacherId")
    public ScheduleTeacherResponse teacherGetByTeacherId(Integer scheduleId, Integer teacherId) {
        TeacherEntity teacher = teacherQueriesService.getById(teacherId);
        return getByTeacher(scheduleId, teacher);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "ScheduleService::getByTeacher", key = "#scheduleId + '-' + #teacher.id")
    public ScheduleTeacherResponse getByTeacher(Integer scheduleId, TeacherEntity teacher) {
        ScheduleEntity schedule = getById(scheduleId);
        List<ScheduleTeacherRowDto> teacherRows = new ArrayList<>();

        for (ScheduleRowDto row : schedule.getRows()) {
            List<ScheduleTeacherColumnDto> teacherColumns = getScheduleTeacherColumnDtos(teacher, row);

            if (!teacherColumns.isEmpty()) {
                ScheduleTeacherRowDto teacherRow = new ScheduleTeacherRowDto(row.shift(), row.groupName(), teacherColumns);
                teacherRows.add(teacherRow);
            }
        }

        List<ScheduleTeacherRowDto> sortedTeacherRows = teacherRows.stream()
                .sorted(Comparator.comparing((ScheduleTeacherRowDto teacherRow) -> {
                    String shift = teacherRow.shift()
                            .toLowerCase()
                            .replace("с ", "")
                            .replace("1 смена", "1 см")
                            .replace("2 смена", "2 см")
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

    private static List<ScheduleTeacherColumnDto> getScheduleTeacherColumnDtos(TeacherEntity teacher, ScheduleRowDto row) {
        List<ScheduleTeacherColumnDto> teacherColumns = new ArrayList<>();

        for (ScheduleColumnDto column : row.columns()) {
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
    public ScheduleEntity add(ScheduleDto schedule, Short departmentId) {
        DepartmentEntity department = departmentService.getById(departmentId);
        ScheduleEntity scheduleEntity = new ScheduleEntity();
        scheduleEntity.setDate(schedule.date());
        scheduleEntity.setRows(schedule.rows());
        scheduleEntity.setDepartment(department);
        return scheduleRepository.save(scheduleEntity);
    }

    @Override
    @Transactional
    @CachePut(cacheNames = "ScheduleService::getById", key = "#result.id")
    public ScheduleEntity updateRowsByDepartmentAndDate(
            Short departmentId,
            LocalDate date,
            List<ScheduleRowDto> rows
    ) {
        ScheduleEntity schedule = scheduleRepository.findByDepartmentIdAndDate(departmentId, date)
                .orElseThrow(() -> new ResourceNotFoundException("Schedule not found"));
        schedule.setRows(rows);
        return scheduleRepository.save(schedule);
    }
}
