package ru.pgk.pgk.features.user.services.role;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pgk.pgk.common.exceptions.ResourceNotFoundException;
import ru.pgk.pgk.features.student.services.StudentService;
import ru.pgk.pgk.features.teacher.service.TeacherService;
import ru.pgk.pgk.features.user.entities.UserEntity;

import static ru.pgk.pgk.common.extensions.BaseExtensions.exist;

@Service
@RequiredArgsConstructor
public class UserRoleServiceImpl implements UserRoleService {

    private final TeacherService teacherService;
    private final StudentService studentService;

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "UserRoleService::getRoleByTelegramId", key = "#telegramId")
    public UserEntity.Role getRoleByTelegramId(Long telegramId) {

        if(exist(() -> studentService.getByTelegramId(telegramId)))
            return UserEntity.Role.STUDENT;
        else if(exist(() -> teacherService.getByTelegramId(telegramId)))
            return UserEntity.Role.TEACHER;

        throw new ResourceNotFoundException("User not found");
    }

}
