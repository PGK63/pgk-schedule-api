package ru.pgk.pgk.features.student.services.cache;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import ru.pgk.pgk.features.student.entities.StudentEntity;

@Service
@RequiredArgsConstructor
public class StudentCacheServiceImpl implements StudentCacheService {

    @Override
    @Caching(evict = {
            @CacheEvict(cacheNames = "StudentService::getById", key = "#student.user.id"),
            @CacheEvict(cacheNames = "StudentService::getByTelegramId", key = "#student.user.telegramId", condition = "#student.user.telegramId!=null"),
            @CacheEvict(cacheNames = "StudentService::getByAliceId", key = "#student.user.aliceId", condition = "#student.user.aliceId!=null")
    })
    public void clearCacheById(StudentEntity student) {}
}
