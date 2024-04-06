package ru.pgk.pgk.features.department.services;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pgk.pgk.common.exceptions.ResourceNotFoundException;
import ru.pgk.pgk.features.department.entitites.DepartmentEntity;
import ru.pgk.pgk.features.department.repositories.DepartmentRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "DepartmentService::getById", key = "#id")
    public DepartmentEntity getById(Short id) {
        return departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found"));
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "DepartmentService::getAll")
    public List<DepartmentEntity> getAll() {
        return departmentRepository.findAll();
    }

    @Override
    @Transactional
    @Caching(
            evict = @CacheEvict(cacheNames = "DepartmentService::getAll", allEntries = true),
            put = @CachePut(cacheNames = "DepartmentService::getById", key = "#result.id")
    )
    public DepartmentEntity add(String name) {
        DepartmentEntity department = new DepartmentEntity();
        department.setName(name);
        return departmentRepository.save(department);
    }

    @Override
    @Transactional
    @Caching(
            evict = {
                    @CacheEvict(cacheNames = "DepartmentService::getAll", allEntries = true),
                    @CacheEvict(cacheNames = "DepartmentService::getById", key = "#id")
            }
    )
    public void deleteById(Short id) {
        departmentRepository.deleteById(id);
    }
}
