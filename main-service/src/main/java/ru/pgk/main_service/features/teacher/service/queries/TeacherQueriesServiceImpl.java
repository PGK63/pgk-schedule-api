package ru.pgk.main_service.features.teacher.service.queries;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pgk.main_service.common.exceptions.ResourceNotFoundException;
import ru.pgk.main_service.features.teacher.entities.TeacherEntity;
import ru.pgk.main_service.features.teacher.repositoties.TeacherRepository;

@Service
@RequiredArgsConstructor
public class TeacherQueriesServiceImpl implements TeacherQueriesService {

    private final TeacherRepository teacherRepository;


    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "TeacherQueriesService::getById", key = "#id")
    public TeacherEntity getById(Integer id) {
        return teacherRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher not found"));
    }
}
