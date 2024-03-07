package ru.pgk.pgk.features.teacher.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pgk.pgk.common.exceptions.ResourceNotFoundException;
import ru.pgk.pgk.features.department.entitites.DepartmentEntity;
import ru.pgk.pgk.features.department.services.DepartmentService;
import ru.pgk.pgk.features.teacher.dto.params.AddTeacherParams;
import ru.pgk.pgk.features.teacher.entities.TeacherEntity;
import ru.pgk.pgk.features.teacher.repositoties.TeacherRepository;
import ru.pgk.pgk.features.teacher.service.cache.TeacherCacheService;
import ru.pgk.pgk.features.user.entities.UserEntity;

@Service
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;

    private final DepartmentService departmentService;
    private final TeacherCacheService teacherCacheService;

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "TeacherService::getById", key = "#id")
    public TeacherEntity getById(Integer id) {
        return teacherRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher not found"));
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "TeacherService::getByTelegramId", key = "#id")
    public TeacherEntity getByTelegramId(Long id) {
        return teacherRepository.findByUserTelegramId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher not found"));
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "TeacherService::getByAliceId", key = "#id")
    public TeacherEntity getByAlicaId(String id) {
        return teacherRepository.findByUserAliceId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher not found"));
    }

    @Override
    @Transactional
    @Caching(
            put = {
                    @CachePut(cacheNames = "TeacherService::getByTelegramId", key = "#telegramId"),
                    @CachePut(cacheNames = "TeacherService::getById", key = "#result.id")
            },
            evict = {
                    @CacheEvict(cacheNames = "UserService::existByTelegramId", key = "#telegramId")
            }
    )
    public TeacherEntity add(Long telegramId, AddTeacherParams params) {
        UserEntity user = new UserEntity();
        user.setTelegramId(telegramId);
        return add(user, params);
    }

    @Override
    @Transactional
    @Caching(
            put = {
                    @CachePut(cacheNames = "TeacherService::getByAliceId", key = "#aliceId"),
                    @CachePut(cacheNames = "TeacherService::getById", key = "#result.id")
            },
            evict = {
                    @CacheEvict(cacheNames = "UserService::existByAliceId", key = "#aliceId")
            }
    )
    public TeacherEntity add(String aliceId, AddTeacherParams params) {
        UserEntity user = new UserEntity();
        user.setAliceId(aliceId);
        return add(user, params);
    }

    private TeacherEntity add(UserEntity user, AddTeacherParams params) {
        TeacherEntity teacher = new TeacherEntity();
        DepartmentEntity department = departmentService.getById(params.departmentId());
        teacher.setFirstName(params.firstName());
        teacher.setLastName(params.lastName());
        teacher.setCabinet(params.cabinet());
        teacher.setDepartment(department);
        teacher.setUser(user);
        return teacherRepository.save(teacher);
    }

    @Override
    @Transactional
    public void updateFirstName(Integer id, String name) {
        TeacherEntity teacher = getById(id);
        updateFirstName(teacher, name);
    }

    @Override
    @Transactional
    public void updateFirstName(Long telegramId, String name) {
        TeacherEntity teacher = getByTelegramId(telegramId);
        updateFirstName(teacher, name);
    }

    @Override
    @Transactional
    public void updateFirstName(String alicaId, String name) {
        TeacherEntity teacher = getByAlicaId(alicaId);
        updateFirstName(teacher, name);
    }

    private void updateFirstName(TeacherEntity teacher, String name) {
        teacher.setFirstName(name);
        teacherRepository.save(teacher);
        clearCache(teacher);
    }

    @Override
    @Transactional
    public void updateLastName(Integer id, String name) {
        TeacherEntity teacher = getById(id);
        updateLastName(teacher, name);
    }

    @Override
    @Transactional
    public void updateLastName(Long telegramId, String name) {
        TeacherEntity teacher = getByTelegramId(telegramId);
        updateLastName(teacher, name);
    }

    @Override
    @Transactional
    public void updateLastName(String alicaId, String name) {
        TeacherEntity teacher = getByAlicaId(alicaId);
        updateLastName(teacher, name);
    }

    private void updateLastName(TeacherEntity teacher, String name) {
        teacher.setLastName(name);
        teacherRepository.save(teacher);
        clearCache(teacher);
    }

    @Override
    @Transactional
    public void updateCabinet(Integer id, String cabinet) {
        TeacherEntity teacher = getById(id);
        updateCabinet(teacher, cabinet);
    }

    @Override
    @Transactional
    public void updateCabinet(Long telegramId, String cabinet) {
        TeacherEntity teacher = getByTelegramId(telegramId);
        updateCabinet(teacher, cabinet);
    }

    @Override
    @Transactional
    public void updateCabinet(String alicaId, String cabinet) {
        TeacherEntity teacher = getByAlicaId(alicaId);
        updateCabinet(teacher, cabinet);
    }

    private void updateCabinet(TeacherEntity teacher, String cabinet) {
        teacher.setCabinet(cabinet);
        teacherRepository.save(teacher);
        clearCache(teacher);
    }


    private void clearCache(TeacherEntity teacher) {
        teacherCacheService.clearCacheById(teacher);
    }
}
