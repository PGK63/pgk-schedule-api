package ru.pgk.pgk.features.teacher.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pgk.pgk.common.exceptions.ResourceNotFoundException;
import ru.pgk.pgk.features.teacher.entities.TeacherEntity;
import ru.pgk.pgk.features.teacher.entities.TeacherUserEntity;
import ru.pgk.pgk.features.teacher.repositoties.TeacherUserRepository;
import ru.pgk.pgk.features.teacher.service.TeacherService;
import ru.pgk.pgk.features.user.entities.TelegramUserEntity;
import ru.pgk.pgk.features.user.entities.UserEntity;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeacherUserServiceImpl implements TeacherUserService {

    private final TeacherUserRepository teacherUserRepository;

    private final TeacherService teacherService;

    @Override
    @Transactional(readOnly = true)
    public List<TeacherUserEntity> getAllByTelegramNotNull(Short departmentId) {
        return teacherUserRepository.findAllByTelegramNotNullAndDepartmentId(departmentId);
    }

    @Override
    @Transactional(readOnly = true)
    public TeacherUserEntity getByTelegramId(Long telegramId) {
        return teacherUserRepository.findByTelegramId(telegramId)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher user not found"));
    }

    @Override
    @Transactional
    public TeacherUserEntity add(Integer teacherId, Long telegramId) {
        TeacherEntity teacher = teacherService.getById(teacherId);

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
