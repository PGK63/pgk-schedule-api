package ru.pgk.pgk.features.department.services;

import ru.pgk.pgk.features.department.entitites.DepartmentEntity;

import java.util.List;

public interface DepartmentService {

    DepartmentEntity getById(Short id);

    List<DepartmentEntity> getAll();
}
