package ru.pgk.main_service.features.teacher.entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.pgk.main_service.features.user.entities.UserEntity;

import java.io.Serializable;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity(name = "teacher_users")
public class TeacherUserEntity implements Serializable {

    @Id
    private Integer id;

    @MapsId
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToOne(fetch = FetchType.LAZY)
    private UserEntity user;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.EAGER)
    private TeacherEntity teacher;
}
