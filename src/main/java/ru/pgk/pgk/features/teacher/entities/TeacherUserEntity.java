package ru.pgk.pgk.features.teacher.entities;

import jakarta.persistence.*;
import lombok.Data;
import ru.pgk.pgk.features.user.entities.UserEntity;

import java.io.Serializable;

@Data
@Entity(name = "teacher_users")
public class TeacherUserEntity implements Serializable {

    @Id
    private Integer id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    private TeacherEntity teacher;
}
