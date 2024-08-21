package ru.pgk.main_service.features.secretKey.services.queries;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pgk.main_service.common.exceptions.ResourceNotFoundException;
import ru.pgk.main_service.features.secretKey.entities.SecretKeyEntity;
import ru.pgk.main_service.features.secretKey.repositories.SecretKeyRepository;

@Service
@RequiredArgsConstructor
public class SecretKeyQueriesServiceImpl implements SecretKeyQueriesService {

    private final SecretKeyRepository secretKeyRepository;

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "SecretKeyService::getByKey", key = "#key")
    public SecretKeyEntity getByKey(String key) {
        return secretKeyRepository.findByKey(key)
                .orElseThrow(() -> new ResourceNotFoundException("Secret key not found"));
    }
}
