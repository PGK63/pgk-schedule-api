package ru.pgk.main_service.features.schedule.service.search;

import org.springframework.data.domain.Page;
import ru.pgk.main_service.features.schedule.entities.ScheduleEntity;

public interface ScheduleSearchService {
    Page<ScheduleEntity> getAllByTeacherId(Integer teacherId, Integer offset);
}
