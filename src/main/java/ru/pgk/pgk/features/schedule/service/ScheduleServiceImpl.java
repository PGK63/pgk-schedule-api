package ru.pgk.pgk.features.schedule.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.scheduling.annotation.Scheduled;
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

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
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

    private final String scriptUrl = "http://api.danbel.ru/pgk/schedule/script";
    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostConstruct
    private void init() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "ScheduleService::getAll")
    public List<ScheduleEntity> getAll(Short departmentId) {
        return scheduleRepository.findAllByDepartmentId(departmentId);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "ScheduleService::studentGetById", key = "#scheduleId-#telegramId")
    public ScheduleStudentResponse studentGetById(Integer scheduleId, Long telegramId) {
        StudentEntity student = studentService.getByTelegramId(telegramId);
        return getByGroupName(scheduleId, student.getGroupName());
    }

    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "ScheduleService::getByGroupName", key = "#name")
    public ScheduleStudentResponse getByGroupName(Integer scheduleId, String name) {
        ScheduleEntity schedule = getById(scheduleId);
        Optional<ScheduleRow> optionalRow = schedule.getRows().stream()
                .filter(r -> r.group_name().contains(name)).findFirst();

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

    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "ScheduleService::getByTeacher", key = "#teacher.id")
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

    @Transactional
    @Scheduled(cron = "0 0 16 * * *")
    public void parseJsonAddDatabase() {
        Schedule schedule = parseJsonScript(true);
        add(schedule, (short) 1);
    }

    @Transactional
    @Scheduled(cron = "0 0 8 * * *")
    @Caching(
            evict = {
                    @CacheEvict(cacheNames = "ScheduleService::getAll"),
                    @CacheEvict(cacheNames = "ScheduleService::getByTeacher", allEntries = true),
                    @CacheEvict(cacheNames = "ScheduleService::teacherGetById", allEntries = true),
                    @CacheEvict(cacheNames = "ScheduleService::getByGroupName", allEntries = true),
                    @CacheEvict(cacheNames = "ScheduleService::studentGetById", allEntries = true)
            }
    )
    public void parseJsonUpdateDatabase() {
        Schedule schedule = parseJsonScript(false);
        updateRowsByDepartmentAndDate((short) 1, schedule.date(), schedule.rows());
    }

    @SneakyThrows
    public Schedule parseJsonScript(Boolean nextDate) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(scriptUrl + "?next_date=" + nextDate))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return objectMapper.readValue(response.body(), Schedule.class);
    }
}
