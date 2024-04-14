package ru.pgk.pgk.features.schedule.service.script;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import retrofit2.Call;
import retrofit2.Response;
import ru.pgk.pgk.features.department.entitites.DepartmentEntity;
import ru.pgk.pgk.features.department.services.DepartmentService;
import ru.pgk.pgk.features.schedule.entities.ScheduleEntity;
import ru.pgk.pgk.features.schedule.entities.json.Schedule;
import ru.pgk.pgk.features.schedule.service.ScheduleService;
import ru.pgk.pgk.features.schedule.service.script.network.ScheduleScriptNetworkService;
import ru.pgk.pgk.features.telegram.services.TelegramService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleScriptServiceImpl implements ScheduleScriptService {

    private final TelegramService telegramService;
    private final DepartmentService departmentService;
    private final ScheduleService scheduleService;

    private final ScheduleScriptNetworkService scheduleScriptNetworkService;

    @Transactional
    @Scheduled(cron = "0 0 16 * * *")
    @Caching(
            evict = {
                    @CacheEvict(cacheNames = "ScheduleService::getAllByDepartmentIdAndOffset", allEntries = true),
                    @CacheEvict(cacheNames = "ScheduleSearchService::getAllByTeacherId", allEntries = true)
            }
    )
    public void parseJsonAddDatabase() {
        List<DepartmentEntity> departments = departmentService.getAll();
        for(DepartmentEntity department : departments) {
            try {
                List<Schedule> schedules = scriptGetSchedules(true, department.getId());
                for(Schedule schedule : schedules) {
                    System.out.println(schedule);
                    ScheduleEntity scheduleEntity = scheduleService.add(schedule, department.getId());
                    telegramService.sendMessageNewSchedule(department.getId(), scheduleEntity.getId());
                }
            }catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Transactional
    @Scheduled(cron = "0 0 8 * * *")
    @Caching(
            evict = {
                    @CacheEvict(cacheNames = "ScheduleService::getByTeacher", allEntries = true),
                    @CacheEvict(cacheNames = "ScheduleService::teacherGetByTelegramId", allEntries = true),
                    @CacheEvict(cacheNames = "ScheduleService::getByStudent", allEntries = true),
                    @CacheEvict(cacheNames = "ScheduleService::studentGetByTelegramId", allEntries = true),
                    @CacheEvict(cacheNames = "ScheduleService::teacherGetByTeacherId", allEntries = true),
            }
    )
    public void parseJsonUpdateDatabase() {
        List<DepartmentEntity> departments = departmentService.getAll();
        for(DepartmentEntity department : departments) {
            List<Schedule> schedules = scriptGetSchedules(false, department.getId());
            for(Schedule schedule : schedules)
                scheduleService.updateRowsByDepartmentAndDate(department.getId(), schedule.date(), schedule.rows());
        }
    }

    @SneakyThrows
    private List<Schedule> scriptGetSchedules(Boolean nextDate, Short departmentId) {
        Call<List<Schedule>> execute =  scheduleScriptNetworkService.getSchedules(nextDate, departmentId);
        Response<List<Schedule>> response = execute.execute();
        return response.body();
    }
}
