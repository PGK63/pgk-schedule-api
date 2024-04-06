package ru.pgk.pgk.features.teacher.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pgk.pgk.common.exceptions.ResourceNotFoundException;
import ru.pgk.pgk.features.teacher.entities.TeacherUserEntity;
import ru.pgk.pgk.features.teacher.repositoties.TeacherUserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeacherUserServiceImpl implements TeacherUserService {

    private final TeacherUserRepository teacherUserRepository;

    @Override
    @Transactional(readOnly = true)
    public List<TeacherUserEntity> getAllByTelegramNotNull(Short departmentId) {
        return teacherUserRepository.findAllByTelegramNotNull(departmentId);
    }

    @Override
    @Transactional(readOnly = true)
    public TeacherUserEntity getByTelegramId(Long telegramId) {
        return teacherUserRepository.findByTelegramId(telegramId)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher user not found"));
    }
}
