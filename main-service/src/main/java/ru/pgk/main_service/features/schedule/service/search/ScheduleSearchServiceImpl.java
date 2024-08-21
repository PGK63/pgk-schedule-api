package ru.pgk.main_service.features.schedule.service.search;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pgk.main_service.features.department.entitites.DepartmentEntity;
import ru.pgk.main_service.features.schedule.entities.ScheduleEntity;
import ru.pgk.main_service.features.schedule.service.ScheduleService;
import ru.pgk.main_service.features.teacher.entities.TeacherEntity;
import ru.pgk.main_service.features.teacher.service.queries.TeacherQueriesService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleSearchServiceImpl implements ScheduleSearchService {

    private final TeacherQueriesService teacherQueriesService;
    private final ScheduleService scheduleService;

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "ScheduleSearchService::getAllByTeacherId", key = "#teacherId.toString() + '-' + #offset")
    public Page<ScheduleEntity> getAllByTeacherId(Integer teacherId, Integer offset) {
        TeacherEntity teacher = teacherQueriesService.getById(teacherId);
        List<Short> departmentIds = teacher.getDepartments().stream().map(DepartmentEntity::getId).toList();
        return scheduleService.getAll(departmentIds, offset);
    }
}
