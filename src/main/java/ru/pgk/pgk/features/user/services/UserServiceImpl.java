package ru.pgk.pgk.features.user.services;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pgk.pgk.common.exceptions.ResourceNotFoundException;
import ru.pgk.pgk.features.user.entities.UserEntity;
import ru.pgk.pgk.features.user.repositoties.UserRepository;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "UserService::getById", key = "#id")
    public UserEntity getById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public UserEntity getByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "UserService::getByTelegramId", key = "#id")
    public UserEntity getByTelegramId(Long id) {
        return userRepository.findByTelegramId(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "UserService::getByAliceId", key = "#id")
    public UserEntity getByAliceId(String id) {
        return userRepository.findByAliceId(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    @Transactional
    @Caching(
            evict = {
                    @CacheEvict(cacheNames = "UserService::getByTelegramId", key = "#id"),
                    @CacheEvict(cacheNames = "UserService::getById", key = "#result.id"),
                    @CacheEvict(cacheNames = "StudentService::getByTelegramId", key = "#id"),
                    @CacheEvict(cacheNames = "TeacherService::getByTelegramId", key = "#id"),
                    @CacheEvict(cacheNames = "ScheduleService::studentGetById", allEntries = true),
                    @CacheEvict(cacheNames = "ScheduleService::teacherGetById", allEntries = true)
            }
    )
    public UserEntity deleteByTelegramId(Long id) {
        UserEntity user = getByTelegramId(id);
        userRepository.delete(user);
        return user;
    }

    @Override
    @Transactional
    @Caching(
            evict = {
                    @CacheEvict(cacheNames = "UserService::getByAliceId", key = "#id"),
                    @CacheEvict(cacheNames = "UserService::getById", key = "#result.id"),
                    @CacheEvict(cacheNames = "StudentService::getByAliceId", key = "#id"),
                    @CacheEvict(cacheNames = "TeacherService::getByAliceId", key = "#id"),
                    @CacheEvict(cacheNames = "ScheduleService::studentGetById", allEntries = true),
                    @CacheEvict(cacheNames = "ScheduleService::teacherGetById", allEntries = true)
            }
    )
    public UserEntity deleteByAliceId(String id) {
        UserEntity user = getByAliceId(id);
        userRepository.delete(user);
        return user;
    }
}
