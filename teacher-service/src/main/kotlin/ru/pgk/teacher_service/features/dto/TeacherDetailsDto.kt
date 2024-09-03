package ru.pgk.teacher_service.features.dto

class TeacherDetailsDto(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val middleName: String?,
    val cabinet: String?,
    val departments: List<DepartmentDto>
)