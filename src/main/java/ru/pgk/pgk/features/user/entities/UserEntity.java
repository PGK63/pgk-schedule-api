package ru.pgk.pgk.features.user.entities;

import jakarta.persistence.*;
import lombok.Data;
import ru.pgk.pgk.features.student.entities.StudentEntity;
import ru.pgk.pgk.features.teacher.entities.TeacherEntity;

import java.io.Serializable;

@Data
@Entity(name = "users")
public class UserEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Long telegramId;
    private String aliceId;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private TeacherEntity teacher;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private StudentEntity student;
}
