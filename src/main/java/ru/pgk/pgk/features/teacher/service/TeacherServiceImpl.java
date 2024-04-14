package ru.pgk.pgk.features.teacher.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pgk.pgk.features.department.services.DepartmentService;
import ru.pgk.pgk.features.teacher.dto.params.AddOrUpdateTeacherParams;
import ru.pgk.pgk.features.teacher.entities.TeacherEntity;
import ru.pgk.pgk.features.teacher.repositoties.TeacherRepository;
import ru.pgk.pgk.features.teacher.service.queries.TeacherQueriesService;

@Service
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;

    private final TeacherQueriesService teacherQueriesService;
    private final DepartmentService departmentService;

    @Override
    @Transactional
    @Caching(
            put = {
                    @CachePut(cacheNames = "TeacherQueriesService::getById", key = "#result.id"),
                    @CachePut(cacheNames = "TeacherQueriesService::getByCabinet", key = "#result.cabinet")
            },
            evict = {
                    @CacheEvict(cacheNames = "TeacherQueriesService::getAllSearchByName", allEntries = true),
                    @CacheEvict(cacheNames = "TeacherQueriesService::getAll", allEntries = true)
            }
    )
    public TeacherEntity add(AddOrUpdateTeacherParams params) {
        TeacherEntity teacher = new TeacherEntity();
        setAddOrUpdateTeacherParams(teacher, params);
        return teacherRepository.save(teacher);
    }

    @Override
    @Transactional
    @Caching(
            put = {
                    @CachePut(cacheNames = "TeacherQueriesService::getById", key = "#result.id"),
                    @CachePut(cacheNames = "TeacherQueriesService::getByCabinet", key = "#result.cabinet")
            },
            evict = {
                    @CacheEvict(cacheNames = "ScheduleSearchService::getAllByTeacherId", key = "#id.toString() + '-' + '*'"),
                    @CacheEvict(cacheNames = "TeacherQueriesService::getAllSearchByName", allEntries = true),
                    @CacheEvict(cacheNames = "TeacherQueriesService::getAll", allEntries = true)
            }
    )
    public TeacherEntity update(Integer id, AddOrUpdateTeacherParams params) {
        TeacherEntity teacher = teacherQueriesService.getById(id);
        setAddOrUpdateTeacherParams(teacher, params);
        return teacherRepository.save(teacher);
    }

    @Override
    @Transactional
    @Caching(
            evict = {
                    @CacheEvict(cacheNames = "TeacherQueriesService::getById", key = "#result.id"),
                    @CacheEvict(cacheNames = "TeacherQueriesService::getByCabinet", key = "#result.cabinet"),
                    @CacheEvict(cacheNames = "TeacherQueriesService::getAllSearchByName", allEntries = true),
                    @CacheEvict(cacheNames = "TeacherQueriesService::getAll", allEntries = true),
                    @CacheEvict(cacheNames = "ScheduleSearchService::getAllByTeacherId", key = "#id.toString() + '-' + '*'")
            }
    )
    public void deleteById(Integer id) {
        teacherRepository.deleteById(id);
    }

    private void setAddOrUpdateTeacherParams(TeacherEntity teacher, AddOrUpdateTeacherParams params) {
        teacher.setFirstName(params.firstName());
        teacher.setLastName(params.lastName());
        teacher.setMiddleName(params.middleName());
        teacher.setCabinet(params.cabinet());
        teacher.setDepartments(params.departmentIds().stream().map(departmentService::getById).toList());
    }
}
