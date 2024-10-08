package ru.pgk.main_service.features.secretKey.services.type;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pgk.main_service.common.exceptions.ResourceNotFoundException;
import ru.pgk.main_service.features.secretKey.entities.SecretKeyTypeEntity;
import ru.pgk.main_service.features.secretKey.repositories.SecretKeyTypeRepository;

@Service
@RequiredArgsConstructor
public class SecretKeyTypeServiceImpl implements SecretKeyTypeService {

    private final SecretKeyTypeRepository secretKeyTypeRepository;

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "SecretKeyTypeService::getByType", key = "#type")
    public SecretKeyTypeEntity getByType(SecretKeyTypeEntity.Type type) {
        return secretKeyTypeRepository.findByValue(type)
                .orElseThrow(() -> new ResourceNotFoundException("Secret key type not found"));
    }
}
