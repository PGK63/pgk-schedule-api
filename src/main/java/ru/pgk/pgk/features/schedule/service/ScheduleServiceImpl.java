package ru.pgk.pgk.features.schedule.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
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
import ru.pgk.pgk.features.schedule.entities.ScheduleEntity;
import ru.pgk.pgk.features.schedule.entities.json.Schedule;
import ru.pgk.pgk.features.schedule.entities.json.ScheduleColumn;
import ru.pgk.pgk.features.schedule.entities.json.ScheduleRow;
import ru.pgk.pgk.features.schedule.repositories.ScheduleRepository;
import ru.pgk.pgk.features.student.entities.StudentEntity;
import ru.pgk.pgk.features.student.services.StudentService;
import ru.pgk.pgk.features.teacher.entities.TeacherEntity;
import ru.pgk.pgk.features.teacher.service.TeacherService;

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
    private final TeacherService teacherService;
    private final DepartmentService departmentService;

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "ScheduleService::getAllByDepartmentIdAndOffset", key = "#departmentId-#offset")
    public Page<ScheduleEntity> getAll(Short departmentId, Integer offset) {
        return scheduleRepository.findAllByDepartmentIdOrderByDateDesc(departmentId, PageRequest.of(offset, 5));
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "ScheduleService::studentGetById", key = "#scheduleId-#telegramId")
    public ScheduleStudentResponse studentGetById(Integer scheduleId, Long telegramId) {
        StudentEntity student = studentService.getByTelegramId(telegramId);
        return getByStudent(scheduleId, student);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "ScheduleService::getByStudent", key = "#scheduleId-#student.id")
    public ScheduleStudentResponse getByStudent(Integer scheduleId, StudentEntity student) {
        ScheduleEntity schedule = getById(scheduleId);
        Optional<ScheduleRow> optionalRow = schedule.getRows().stream()
                .filter(r -> r.group_name().contains(student.getGroupName())).findFirst();

        if(optionalRow.isEmpty()) throw new ResourceNotFoundException("Schedule not found");
        ScheduleRow row = optionalRow.get();

        return new ScheduleStudentResponse(
                schedule.getDate(),
                row.shift(),
                row.columns()
        );
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "ScheduleService::teacherGetById", key = "#scheduleId-#telegramId")
    public ScheduleTeacherResponse teacherGetById(Integer scheduleId, Long telegramId) {
        TeacherEntity teacher = teacherService.getByTelegramId(telegramId);
        return getByTeacher(scheduleId, teacher);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "ScheduleService::getByTeacher", key = "#scheduleId-#teacher.id")
    public ScheduleTeacherResponse getByTeacher(Integer scheduleId, TeacherEntity teacher) {
       ScheduleEntity schedule = getById(scheduleId);
       List<ScheduleTeacherColumnDto> teacherColumns = new ArrayList<>();

       for(ScheduleRow row : schedule.getRows()) {
           for (ScheduleColumn column : row.columns()) {

               if((column.teacher() == null && teacher.getCabinet() != null && column.cabinet() != null
                       && column.cabinet().contains(teacher.getCabinet()))
                       || (column.teacher() != null
                       && column.teacher().equals(teacher.getFIO()))
               ) {
                   ScheduleTeacherColumnDto teacherColumn = new ScheduleTeacherColumnDto(
                           column.number(),
                           row.shift(),
                           row.group_name(),
                           column.cabinet(),
                           column.exam()
                   );
                   teacherColumns.add(teacherColumn);
               }
           }
       }

       return new ScheduleTeacherResponse(
               schedule.getDate(),
               teacherColumns.stream().sorted(Comparator.comparing(ScheduleTeacherColumnDto::number)).toList()
       );
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
