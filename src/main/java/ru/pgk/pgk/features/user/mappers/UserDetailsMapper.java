package ru.pgk.pgk.features.user.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.pgk.pgk.common.mapper.Mappable;
import ru.pgk.pgk.features.student.mapper.StudentDetailsMapper;
import ru.pgk.pgk.features.teacher.mappers.TeacherDetailsMapper;
import ru.pgk.pgk.features.user.dto.UserDetailsDto;
import ru.pgk.pgk.features.user.entities.UserEntity;

@Mapper(componentModel = "spring", uses = {StudentDetailsMapper.class, TeacherDetailsMapper.class})
public interface UserDetailsMapper extends Mappable<UserEntity, UserDetailsDto> {

    @Override
    @Mapping(target = "role", expression = "java(entity.getRole())")
    @Mapping(source = "entity.teacher.teacher", target = "teacher")
    @Mapping(source = "entity.alice.aliceId", target = "aliceId")
    UserDetailsDto toDto(UserEntity entity);
}
