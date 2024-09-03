package ru.pgk.teacher_service.features.entities

import jakarta.persistence.*
import java.io.Serializable

@Entity(name = "teachers")
data class TeacherEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,
    var firstName: String = "",
    var lastName: String = "",
    var middleName: String? = null,
    var cabinet: String? = null,

    @JoinTable(
        name = "teachers_departments",
        joinColumns = [JoinColumn(name = "teacher_id")],
        inverseJoinColumns = [JoinColumn(name = "department_id")]
    )
    @ManyToMany(fetch = FetchType.EAGER)
    var departments: List<DepartmentEntity> = emptyList()
): Serializable
