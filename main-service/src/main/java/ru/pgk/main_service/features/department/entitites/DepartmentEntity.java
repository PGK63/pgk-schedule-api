package ru.pgk.main_service.features.department.entitites;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.pgk.main_service.features.group.entities.GroupEntity;
import ru.pgk.main_service.features.schedule.entities.ScheduleEntity;
import ru.pgk.main_service.features.teacher.entities.TeacherEntity;

import java.io.Serializable;
import java.util.Collection;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity(name = "departments")
public class DepartmentEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Short id;

    private String name;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY)
    private Collection<GroupEntity> groups;


    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany(mappedBy = "departments", fetch = FetchType.LAZY)
    private Collection<TeacherEntity> teachers;


    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY)
    private Collection<ScheduleEntity> schedules;
}
