package ru.pgk.teacher_service.features.repositories

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import ru.pgk.teacher_service.features.entities.TeacherEntity
import java.util.*

interface TeacherRepository: JpaRepository<TeacherEntity, Int> {

    @Query(
        "SELECT teacher FROM teachers teacher " +
                "WHERE LOWER(teacher.lastName) LIKE %?1% " +
                "OR LOWER(teacher.firstName) LIKE %?1% OR " +
                "LOWER(teacher.middleName) LIKE %?1%"
    )
    fun search(name: String, pageable: Pageable): Page<TeacherEntity>

    fun findByCabinet(cabinet: String): Optional<TeacherEntity>
}