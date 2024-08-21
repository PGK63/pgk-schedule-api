package ru.pgk.pgk.features.admin.services;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pgk.pgk.features.admin.dto.param.CreateAdminParams;
import ru.pgk.pgk.features.admin.entities.AdminEntity;
import ru.pgk.pgk.features.admin.entities.AdminTypeEntity;
import ru.pgk.pgk.features.admin.repositories.AdminRepository;
import ru.pgk.pgk.features.admin.services.type.AdminTypeService;
import ru.pgk.pgk.features.user.entities.UserEntity;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;

    private final AdminTypeService adminTypeService;

    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "AdminService::getAll")
    public List<AdminEntity> getAll() {
        return adminRepository.findAll();
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = "AdminService::getAll", allEntries = true)
    public AdminEntity add(CreateAdminParams params) {
        AdminEntity admin = new AdminEntity();
        UserEntity user = new UserEntity();
        AdminTypeEntity typeEntity = adminTypeService.getByType(params.type());
        admin.setType(typeEntity);

        admin.setUsername(params.username());
        admin.setPassword(passwordEncoder.encode(params.password()));

        admin.setUser(user);

        return adminRepository.save(admin);
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = "AdminService::getAll", allEntries = true)
    public void delete(Integer id) {
        adminRepository.deleteById(id);
    }
}
