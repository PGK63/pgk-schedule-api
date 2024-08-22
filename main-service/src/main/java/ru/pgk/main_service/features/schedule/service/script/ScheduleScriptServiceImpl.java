package ru.pgk.main_service.features.schedule.service.script;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pgk.main_service.features.department.entitites.DepartmentEntity;
import ru.pgk.main_service.features.department.services.DepartmentService;
import ru.pgk.main_service.features.schedule.entities.ScheduleEntity;
import ru.pgk.main_service.features.schedule.dto.script.ScheduleDto;
import ru.pgk.main_service.features.schedule.mappers.grpc.script.ScheduleReplyMapper;
import ru.pgk.main_service.features.schedule.service.ScheduleService;
import ru.pgk.main_service.features.telegram.services.TelegramService;
import ru.pgk.schedule_service.lib.ScheduleReply;
import ru.pgk.schedule_service.lib.ScheduleRequest;
import ru.pgk.schedule_service.lib.ScheduleScriptServiceGrpc;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleScriptServiceImpl implements ScheduleScriptService {

    private final TelegramService telegramService;
    private final DepartmentService departmentService;
    private final ScheduleService scheduleService;

    private final ScheduleReplyMapper scheduleReplyMapper;

    @GrpcClient("schedule-script")
    private ScheduleScriptServiceGrpc.ScheduleScriptServiceBlockingStub scheduleScriptService;

    @Override
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
                List<ScheduleDto> schedules = scriptGetSchedules(true, department.getId());
                for(ScheduleDto schedule : schedules) {
                    try {
                        System.out.println(schedule);
                        ScheduleEntity scheduleEntity = scheduleService.add(schedule, department.getId());
                        telegramService.sendMessageNewSchedule(department.getId(), scheduleEntity.getId());
                    }catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
            }catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    @Transactional
    @Scheduled(cron = "0 0 8 * * *")
    @Caching(
            evict = {
                    @CacheEvict(cacheNames = "ScheduleService::getByTeacher", allEntries = true),
                    @CacheEvict(cacheNames = "ScheduleService::teacherGetByTelegramId", allEntries = true),
                    @CacheEvict(cacheNames = "ScheduleService::getByStudent", allEntries = true),
                    @CacheEvict(cacheNames = "ScheduleService::studentGetByTelegramId", allEntries = true),
                    @CacheEvict(cacheNames = "ScheduleService::teacherGetByTeacherId", allEntries = true),
                    @CacheEvict(cacheNames = "ScheduleService::studentGetByAliceId", allEntries = true),
                    @CacheEvict(cacheNames = "ScheduleService::teacherGetByAliceId", allEntries = true),
            }
    )
    public void parseJsonUpdateDatabase() {
        List<DepartmentEntity> departments = departmentService.getAll();
        for(DepartmentEntity department : departments) {
            List<ScheduleDto> schedules = scriptGetSchedules(false, department.getId());
            for(ScheduleDto schedule : schedules)
                scheduleService.updateRowsByDepartmentAndDate(department.getId(), schedule.date(), schedule.rows());
        }
    }

    @SneakyThrows
    private List<ScheduleDto> scriptGetSchedules(Boolean nextDate, Short departmentId) {
        ScheduleRequest request = ScheduleRequest.newBuilder()
                .setNextDate(nextDate)
                .setDepartmentId(departmentId)
                .build();

        List<ScheduleReply> schedulesReply = scheduleScriptService.parseScheduleGoogleSheet(request).getSchedulesList();
        return scheduleReplyMapper.toDto(schedulesReply);
    }
}
