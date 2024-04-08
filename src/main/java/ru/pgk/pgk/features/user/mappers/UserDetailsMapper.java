package ru.pgk.pgk.features.user.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.pgk.pgk.common.mapper.Mappable;
import ru.pgk.pgk.features.student.dto.StudentDetailsDto;
import ru.pgk.pgk.features.student.mapper.StudentDetailsMapper;
import ru.pgk.pgk.features.teacher.dto.TeacherDetailsDto;
import ru.pgk.pgk.features.teacher.mappers.TeacherDetailsMapper;
import ru.pgk.pgk.features.user.dto.UserDetailsDto;
import ru.pgk.pgk.features.user.entities.UserEntity;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserDetailsMapper implements Mappable<UserEntity, UserDetailsDto> {

    private final StudentDetailsMapper studentDetailsMapper;
    private final TeacherDetailsMapper teacherDetailsMapper;

    @Override
    public UserDetailsDto toDto(UserEntity entity) {
        TeacherDetailsDto teacherDetailsDto = null;
        StudentDetailsDto studentDetailsDto = null;
        UserEntity.Role role = entity.getRole();

        if(role == UserEntity.Role.STUDENT) {
            studentDetailsDto = studentDetailsMapper.toDto(entity.getStudent());
        }else if(role == UserEntity.Role.TEACHER) {
            teacherDetailsDto = teacherDetailsMapper.toDto(entity.getTeacher().getTeacher());
        }

        return new UserDetailsDto(
                entity.getId(),
                role,
                studentDetailsDto,
                teacherDetailsDto
        );
    }

    @Override
    public List<UserDetailsDto> toDto(List<UserEntity> entity) {
        return entity.stream().map(this::toDto).toList();
    }

    @Override
    public UserEntity toEntity(UserDetailsDto dto) {
        return null;
    }

    @Override
    public List<UserEntity> toEntity(List<UserDetailsDto> dtos) {
        return null;
    }
}
