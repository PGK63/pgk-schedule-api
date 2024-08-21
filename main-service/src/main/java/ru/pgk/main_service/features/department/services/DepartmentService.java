package ru.pgk.main_service.features.department.services;

import ru.pgk.main_service.features.department.entitites.DepartmentEntity;

import java.util.List;

public interface DepartmentService {

    DepartmentEntity getById(Short id);

    List<DepartmentEntity> getAll();

    DepartmentEntity add(String name);

    DepartmentEntity update(Short id, String name);

    void deleteById(Short id);
}
