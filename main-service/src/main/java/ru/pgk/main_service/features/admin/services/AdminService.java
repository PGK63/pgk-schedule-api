package ru.pgk.main_service.features.admin.services;

import ru.pgk.main_service.features.admin.dto.param.CreateAdminParams;
import ru.pgk.main_service.features.admin.entities.AdminEntity;

import java.util.List;

public interface AdminService {

    List<AdminEntity> getAll();

    AdminEntity add(CreateAdminParams params);

    void delete(Integer id);
}
