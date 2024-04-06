package ru.pgk.pgk.features.admin.services.type;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pgk.pgk.common.exceptions.ResourceNotFoundException;
import ru.pgk.pgk.features.admin.entities.AdminTypeEntity;
import ru.pgk.pgk.features.admin.repositories.AdminTypeRepository;

@Service
@RequiredArgsConstructor
public class AdminTypeServiceImpl implements AdminTypeService {

    private final AdminTypeRepository adminTypeRepository;

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "AdminTypeService::getByType", key = "#type")
    public AdminTypeEntity getByType(AdminTypeEntity.Type type) {
        return adminTypeRepository.findByType(type)
                .orElseThrow(() -> new ResourceNotFoundException("Admin type not found"));
    }
}
