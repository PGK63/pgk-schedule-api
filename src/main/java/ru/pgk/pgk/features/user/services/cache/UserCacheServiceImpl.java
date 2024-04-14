package ru.pgk.pgk.features.user.services.cache;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import ru.pgk.pgk.features.user.entities.UserEntity;

@Service
@RequiredArgsConstructor
public class UserCacheServiceImpl implements UserCacheService {

    @Override
    @Caching(
            evict = {
                    @CacheEvict(cacheNames = "UserService::getByTelegramId", key = "#user.telegram.telegramId"),
                    @CacheEvict(cacheNames = "UserService::getById", key = "#user.id"),

                    @CacheEvict(cacheNames = "StudentService::getById", key = "#user.student.id"),
                    @CacheEvict(cacheNames = "StudentService::getByTelegramId", key = "#user.telegram.telegramId"),
                    @CacheEvict(cacheNames = "StudentService::getByAliceId", key = "#user.alice.id"),

                    @CacheEvict(cacheNames = "ScheduleService::studentGetByTelegramId", key = "'*' + '-' + #user.telegram.telegramId"),
                    @CacheEvict(cacheNames = "ScheduleService::getByStudent", key = "'*' + '-' + #user.student.id"),

                    @CacheEvict(cacheNames = "ScheduleService::teacherGetByTelegramId", key =  "'*' + '-' + #user.telegram.telegramId"),
                    @CacheEvict(cacheNames = "ScheduleService::getByTeacher", key = "'*' + '-' + #user.teacher.id"),

                    @CacheEvict(cacheNames = "TeacherUserService::getByTelegramId", key = "#user.telegram.telegramId")
            }
    )
    public void clearAll(UserEntity user) {}
}
