package ru.pgk.main_service.features.teacher.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pgk.main_service.common.exceptions.ResourceNotFoundException;
import ru.pgk.main_service.features.teacher.entities.TeacherEntity;
import ru.pgk.main_service.features.teacher.entities.TeacherUserEntity;
import ru.pgk.main_service.features.teacher.repositoties.TeacherUserRepository;
import ru.pgk.main_service.features.teacher.service.queries.TeacherQueriesService;
import ru.pgk.main_service.features.user.entities.TelegramUserEntity;
import ru.pgk.main_service.features.user.entities.UserEntity;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeacherUserServiceImpl implements TeacherUserService {

    private final TeacherUserRepository teacherUserRepository;

    private final TeacherQueriesService teacherQueriesService;

    @Override
    @Transactional(readOnly = true)
    public List<TeacherUserEntity> getAllByTelegramNotNull(Short departmentId) {
        return teacherUserRepository.findAllByTelegramNotNullAndDepartmentId(departmentId);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "TeacherUserService::getByTelegramId", key = "#telegramId")
    public TeacherUserEntity getByTelegramId(Long telegramId) {
        return teacherUserRepository.findByTelegramId(telegramId)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher user not found"));
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "TeacherUserService::getByAliceId", key = "#aliceId")
    public TeacherUserEntity getByAliceId(String aliceId) {
        return teacherUserRepository.findByAliceId(aliceId)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher user not found"));
    }

    @Override
    @Transactional
    @Caching(
            put = {
                    @CachePut(cacheNames = "TeacherUserService::getByTelegramId", key = "#teacherId"),
                    @CachePut(cacheNames = "TeacherUserService::getByAliceId", key = "#result.user.alice.aliceId", condition = "#result.user.alice != null"),
            }
    )
    public TeacherUserEntity add(Integer teacherId, Long telegramId) {
        TeacherEntity teacher = teacherQueriesService.getById(teacherId);

        UserEntity user = new UserEntity();

        TelegramUserEntity telegram = new TelegramUserEntity();
        telegram.setTelegramId(telegramId);
        telegram.setUser(user);

        user.setTelegram(telegram);

        TeacherUserEntity teacherUser = new TeacherUserEntity();
        teacherUser.setTeacher(teacher);
        teacherUser.setUser(user);

        return teacherUserRepository.save(teacherUser);
    }
}
