package ru.pgk.pgk.features.schedule.service.script;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pgk.pgk.features.department.entitites.DepartmentEntity;
import ru.pgk.pgk.features.department.services.DepartmentService;
import ru.pgk.pgk.features.schedule.entities.ScheduleEntity;
import ru.pgk.pgk.features.schedule.entities.json.Schedule;
import ru.pgk.pgk.features.schedule.service.ScheduleService;
import ru.pgk.pgk.features.telegram.services.TelegramService;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleScriptServiceImpl implements ScheduleScriptService {

    private final String scriptUrl = "http://api.danbel.ru/pgk/schedule/script";
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final TelegramService telegramService;
    private final DepartmentService departmentService;
    private final ScheduleService scheduleService;

    @PostConstruct
    private void init() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

    @Transactional
    @Scheduled(cron = "0 10 16 * * *")
    @CacheEvict(cacheNames = "ScheduleService::getAll", allEntries = true)
    public void parseJsonAddDatabase() {
        List<DepartmentEntity> departments = departmentService.getAll();
        for(DepartmentEntity department : departments) {
            List<Schedule> schedules = parseJsonScript(true, department.getId());
            for(Schedule schedule : schedules) {
                ScheduleEntity scheduleEntity = scheduleService.add(schedule, department.getId());
                telegramService.sendMessageNewSchedule(department.getId(), scheduleEntity.getId());
            }
        }
    }

    @Transactional
    @Scheduled(cron = "0 0 8 * * *")
    @Caching(
            evict = {
                    @CacheEvict(cacheNames = "ScheduleService::getByTeacher", allEntries = true),
                    @CacheEvict(cacheNames = "ScheduleService::teacherGetById", allEntries = true),
                    @CacheEvict(cacheNames = "ScheduleService::getByStudent", allEntries = true),
                    @CacheEvict(cacheNames = "ScheduleService::studentGetById", allEntries = true)
            }
    )
    public void parseJsonUpdateDatabase() {
        List<DepartmentEntity> departments = departmentService.getAll();
        for(DepartmentEntity department : departments) {
            List<Schedule> schedules = parseJsonScript(false, department.getId());
            for(Schedule schedule : schedules)
                scheduleService.updateRowsByDepartmentAndDate(department.getId(), schedule.date(), schedule.rows());
        }
    }

    @SneakyThrows
    private List<Schedule> parseJsonScript(Boolean nextDate, Short departmentId) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(scriptUrl + "?next_date=" + nextDate + "&department_id=" + departmentId))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return objectMapper.readValue(response.body(), new TypeReference<>() {});
    }
}
