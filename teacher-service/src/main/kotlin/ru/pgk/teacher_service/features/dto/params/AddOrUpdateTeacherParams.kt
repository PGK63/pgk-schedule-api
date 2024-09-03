package ru.pgk.teacher_service.features.dto.params

data class AddOrUpdateTeacherParams(
    val firstName: String,
    val lastName: String,
    val middleName: String?,
    val cabinet: String?,
    val departmentIds: List<Short>
)
