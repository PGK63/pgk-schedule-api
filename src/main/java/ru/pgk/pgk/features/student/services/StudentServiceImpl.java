package ru.pgk.pgk.features.student.services;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pgk.pgk.common.exceptions.ResourceNotFoundException;
import ru.pgk.pgk.features.group.entities.GroupEntity;
import ru.pgk.pgk.features.group.services.GroupService;
import ru.pgk.pgk.features.student.dto.params.AddStudentParams;
import ru.pgk.pgk.features.student.entities.StudentEntity;
import ru.pgk.pgk.features.student.repositoties.StudentRepository;
import ru.pgk.pgk.features.user.entities.AliceUserEntity;
import ru.pgk.pgk.features.user.entities.TelegramUserEntity;
import ru.pgk.pgk.features.user.entities.UserEntity;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final GroupService groupService;

    private final StudentRepository studentRepository;

    @Override
    @Transactional(readOnly = true)
    public List<StudentEntity> getAllByTelegramNotNull(Short departmentId) {
        return studentRepository.findAllByDepartmentIdAndTelegramNotNull(departmentId);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "StudentService::getById", key = "#id")
    public StudentEntity getById(Integer id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "StudentService::getByTelegramId", key = "#id")
    public StudentEntity getByTelegramId(Long id) {
        return studentRepository.findByUserTelegramId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "StudentService::getByAliceId", key = "#id")
    public StudentEntity getByAliceId(String id) {
        return studentRepository.findByUserAliceId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
    }

    @Override
    @Transactional
    @Caching(
            put = {
                    @CachePut(cacheNames = "StudentService::getById", key = "#result.id"),
                    @CachePut(cacheNames = "StudentService::getByTelegramId", key = "#telegramId")
            },
            evict = {
                    @CacheEvict(cacheNames = "UserService::getById", key = "#result.id")
            }
    )
    public StudentEntity add(Long telegramId, AddStudentParams params) {
        UserEntity user = new UserEntity();
        TelegramUserEntity telegramUser = new TelegramUserEntity();
        telegramUser.setTelegramId(telegramId);
        telegramUser.setUser(user);
        user.setTelegram(telegramUser);
        return add(user, params);
    }

    @Override
    @Transactional
    @Caching(
            put = {
                    @CachePut(cacheNames = "StudentService::getById", key = "#result.id"),
                    @CachePut(cacheNames = "StudentService::getByAliceId", key = "#aliceId")
            },
            evict = {
                    @CacheEvict(cacheNames = "UserService::getById", key = "#result.id")
            }
    )
    public StudentEntity add(String aliceId, AddStudentParams params) {
        UserEntity user = new UserEntity();
        AliceUserEntity aliceUser = new AliceUserEntity();
        aliceUser.setAliceId(aliceId);
        aliceUser.setUser(user);
        user.setAlice(aliceUser);
        return add(user, params);
    }

    private StudentEntity add(UserEntity user, AddStudentParams params) {
        StudentEntity student = new StudentEntity();
        GroupEntity group = groupService.add(params.groupName(), params.departmentId());
        student.setUser(user);
        student.setGroup(group);
        return studentRepository.save(student);
    }
}
