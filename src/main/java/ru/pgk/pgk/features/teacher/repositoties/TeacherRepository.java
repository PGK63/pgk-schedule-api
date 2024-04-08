package ru.pgk.pgk.features.teacher.repositoties;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.pgk.pgk.features.teacher.entities.TeacherEntity;

public interface TeacherRepository extends JpaRepository<TeacherEntity, Integer> {

    @Query(
            "SELECT teacher FROM teachers teacher " +
                    "WHERE LOWER(teacher.lastName) LIKE %?1% " +
                    "OR LOWER(teacher.firstName) LIKE %?1% OR " +
                    "LOWER(teacher.middleName) LIKE %?1%" +
                    "ORDER BY teacher.lastName, teacher.firstName, teacher.middleName"
    )
    Page<TeacherEntity> search(String name, Pageable pageable);
}
