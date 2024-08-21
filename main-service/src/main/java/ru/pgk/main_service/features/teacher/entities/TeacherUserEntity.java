package ru.pgk.main_service.features.teacher.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.pgk.main_service.features.user.entities.UserEntity;

import java.io.Serializable;

@Getter
@Setter
@Entity(name = "teacher_users")
public class TeacherUserEntity implements Serializable {

    @Id
    private Integer id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    private UserEntity user;

    @ManyToOne(fetch = FetchType.EAGER)
    private TeacherEntity teacher;
}
