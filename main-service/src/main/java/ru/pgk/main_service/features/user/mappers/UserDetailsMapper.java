package ru.pgk.main_service.features.user.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.pgk.main_service.common.mapper.Mappable;
import ru.pgk.main_service.features.student.mapper.StudentDetailsMapper;
import ru.pgk.main_service.features.teacher.mappers.TeacherDetailsMapper;
import ru.pgk.main_service.features.user.dto.UserDetailsDto;
import ru.pgk.main_service.features.user.entities.UserEntity;

@Mapper(componentModel = "spring", uses = {StudentDetailsMapper.class, TeacherDetailsMapper.class})
public interface UserDetailsMapper extends Mappable<UserEntity, UserDetailsDto> {

    @Override
    @Mapping(target = "role", expression = "java(entity.getRole())")
    @Mapping(source = "entity.teacher.teacher", target = "teacher")
    @Mapping(source = "entity.alice.aliceId", target = "aliceId")
    UserDetailsDto toDto(UserEntity entity);
}
