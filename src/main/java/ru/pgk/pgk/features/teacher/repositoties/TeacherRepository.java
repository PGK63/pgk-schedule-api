package ru.pgk.pgk.features.teacher.repositoties;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pgk.pgk.features.teacher.entities.TeacherEntity;

public interface TeacherRepository extends JpaRepository<TeacherEntity, Integer> {}
