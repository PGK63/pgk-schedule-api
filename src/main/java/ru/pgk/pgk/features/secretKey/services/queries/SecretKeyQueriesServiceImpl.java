package ru.pgk.pgk.features.secretKey.services.queries;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pgk.pgk.common.exceptions.ResourceNotFoundException;
import ru.pgk.pgk.features.secretKey.entities.SecretKeyEntity;
import ru.pgk.pgk.features.secretKey.repositories.SecretKeyRepository;

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
