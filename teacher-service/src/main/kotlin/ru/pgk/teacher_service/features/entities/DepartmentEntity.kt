package ru.pgk.teacher_service.features.entities

import jakarta.persistence.*
import java.io.Serializable

@Entity(name = "departments")
data class DepartmentEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,
    var name: String,

    @ManyToMany(mappedBy = "departments", fetch = FetchType.LAZY)
    val teachers: List<TeacherEntity> = emptyList()
): Serializable
