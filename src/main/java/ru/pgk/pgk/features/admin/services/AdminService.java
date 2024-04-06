package ru.pgk.pgk.features.admin.services;

import ru.pgk.pgk.features.admin.dto.param.CreateAdminParams;
import ru.pgk.pgk.features.admin.entities.AdminEntity;
import ru.pgk.pgk.features.admin.entities.AdminTypeEntity;

import java.util.List;

public interface AdminService {

    List<AdminEntity> getAll();

    AdminEntity add(CreateAdminParams params);

    void delete(Integer id);
}
