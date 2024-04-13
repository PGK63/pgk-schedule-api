package ru.pgk.pgk.features.schedule.service.search;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pgk.pgk.features.department.entitites.DepartmentEntity;
import ru.pgk.pgk.features.schedule.entities.ScheduleEntity;
import ru.pgk.pgk.features.schedule.service.ScheduleService;
import ru.pgk.pgk.features.teacher.entities.TeacherEntity;
import ru.pgk.pgk.features.teacher.service.TeacherService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleSearchServiceImpl implements ScheduleSearchService {

    private final TeacherService teacherService;
    private final ScheduleService scheduleService;

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "ScheduleSearchService::getAllByTeacherId", key = "#teacherId.toString() + '-' + #offset")
    public Page<ScheduleEntity> getAllByTeacherId(Integer teacherId, Integer offset) {
        TeacherEntity teacher = teacherService.getById(teacherId);
        List<Short> departmentIds = teacher.getDepartments().stream().map(DepartmentEntity::getId).toList();
        return scheduleService.getAll(departmentIds, offset);
    }
}
