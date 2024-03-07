package ru.pgk.pgk.features.teacher.service.cache;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import ru.pgk.pgk.features.teacher.entities.TeacherEntity;

@Service
@RequiredArgsConstructor
public class TeacherCacheServiceImpl implements TeacherCacheService {

    @Override
    @Caching(evict = {
            @CacheEvict(cacheNames = "TeacherService::getById", key = "#teacher.user.id"),
            @CacheEvict(cacheNames = "TeacherService::getByTelegramId", key = "#teacher.user.telegramId", condition = "#teacher.user.telegramId!=null"),
            @CacheEvict(cacheNames = "TeacherService::getByAliceId", key = "#teacher.user.aliceId", condition = "#teacher.user.aliceId!=null")
    })
    public void clearCacheById(TeacherEntity teacher){}
}
