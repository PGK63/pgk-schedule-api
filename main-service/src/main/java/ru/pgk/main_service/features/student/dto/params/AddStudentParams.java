package ru.pgk.main_service.features.student.dto.params;

public record AddStudentParams(
   String groupName,
   Short departmentId
) {}
