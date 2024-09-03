package ru.pgk.main_service.features.user.entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.pgk.main_service.features.admin.entities.AdminEntity;
import ru.pgk.main_service.features.secretKey.entities.SecretKeyEntity;
import ru.pgk.main_service.features.student.entities.StudentEntity;
import ru.pgk.main_service.features.teacher.entities.TeacherUserEntity;

import java.io.Serializable;
import java.util.Collection;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity(name = "users")
public class UserEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private AliceUserEntity alice;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private TelegramUserEntity telegram;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToOne(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private StudentEntity student;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToOne(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private TeacherUserEntity teacher;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private AdminEntity admin;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "id.user", fetch = FetchType.LAZY)
    private Collection<SecretKeyEntity> secretKeys;

    public Role getRole() {
        if(teacher != null)
            return Role.TEACHER;
        else if(student != null)
            return Role.STUDENT;
        return null;
    }

    public enum Role {
        STUDENT,
        TEACHER
    }
}
