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
                    @CacheEvict(cacheNames = "UserService::getByTelegramId", key = "#user.telegram.telegramId", condition = "#user.telegram != null"),
                    @CacheEvict(cacheNames = "UserService::getById", key = "#user.id"),

                    @CacheEvict(cacheNames = "StudentService::getById", key = "#user.student.id", condition = "#user.student != null"),
                    @CacheEvict(cacheNames = "StudentService::getByTelegramId", key = "#user.telegram.telegramId", condition = "#user.telegram != null"),
                    @CacheEvict(cacheNames = "StudentService::getByAliceId", key = "#user.alice.id", condition = "#user.alice != null"),

                    @CacheEvict(cacheNames = "ScheduleService::studentGetByTelegramId", key = "'*' + '-' + #user.telegram.telegramId", condition = "#user.telegram != null"),
                    @CacheEvict(cacheNames = "ScheduleService::getByStudent", key = "'*' + '-' + #user.student.id", condition = "#user.student != null"),

                    @CacheEvict(cacheNames = "ScheduleService::teacherGetByTelegramId", key =  "'*' + '-' + #user.telegram.telegramId", condition = "#user.telegram != null"),
                    @CacheEvict(cacheNames = "ScheduleService::getByTeacher", key = "'*' + '-' + #user.teacher.id", condition = "#user.teacher != null"),

                    @CacheEvict(cacheNames = "TeacherUserService::getByTelegramId", key = "#user.telegram.telegramId", condition = "#user.telegram != null")
            }
    )
    public void clearAll(UserEntity user) {}
}
