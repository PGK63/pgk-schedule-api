package ru.pgk.pgk.features.teacher.service.queries;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pgk.pgk.common.exceptions.ResourceNotFoundException;
import ru.pgk.pgk.features.teacher.entities.TeacherEntity;
import ru.pgk.pgk.features.teacher.repositoties.TeacherRepository;

@Service
@RequiredArgsConstructor
public class TeacherQueriesServiceImpl implements TeacherQueriesService {

    private final TeacherRepository teacherRepository;

    @Value("${base.page_size}")
    private Integer pageSize;

    @Override
    @Transactional(readOnly = true)
    @Caching(
            cacheable = {
                    @Cacheable(
                            cacheNames = "TeacherQueriesService::getAllSearchByName",
                            condition = "#name != null",
                            key = "#name.toString() + '-' + #offset"
                    ),
                    @Cacheable(
                            cacheNames = "TeacherQueriesService::getAll",
                            condition = "#name == null",
                            key = "#offset"
                    )
            }
    )
    public Page<TeacherEntity> getAll(String name, Integer offset) {
        if(name != null)
            return teacherRepository.search(name.trim().toLowerCase(), PageRequest.of(offset, pageSize));
        else
            return teacherRepository.findAll(
                    PageRequest.of(offset, pageSize, Sort.by("lastName", "firstName", "middleName"))
            );
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "TeacherQueriesService::getById", key = "#id")
    public TeacherEntity getById(Integer id) {
        return teacherRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher not found"));
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "TeacherQueriesService::getByCabinet", key = "#cabinet.toString()")
    public TeacherEntity getByCabinet(String cabinet) {
        return teacherRepository.findByCabinet(cabinet)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher not found"));
    }
}
