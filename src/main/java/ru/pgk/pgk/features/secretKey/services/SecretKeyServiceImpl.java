package ru.pgk.pgk.features.secretKey.services;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pgk.pgk.common.exceptions.ResourceNotFoundException;
import ru.pgk.pgk.features.secretKey.entities.SecretKeyEntity;
import ru.pgk.pgk.features.secretKey.entities.SecretKeyTypeEntity;
import ru.pgk.pgk.features.secretKey.repositories.SecretKeyRepository;
import ru.pgk.pgk.features.secretKey.services.type.SecretKeyTypeService;
import ru.pgk.pgk.features.user.entities.UserEntity;
import ru.pgk.pgk.features.user.services.queries.UserQueriesService;

import java.util.Optional;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class SecretKeyServiceImpl implements SecretKeyService {

    private final SecretKeyRepository secretKeyRepository;

    private final SecretKeyTypeService secretKeyTypeService;
    private final UserQueriesService userQueriesService;

    private final CacheManager cacheManager;

    private final Random random = new Random();
    private final ScheduledExecutorService schedulerRemoveCode = Executors.newScheduledThreadPool(1);

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "SecretKeyService::getByKey", key = "#key")
    public SecretKeyEntity getByKey(String key) {
        return secretKeyRepository.findByKey(key)
                .orElseThrow(() -> new ResourceNotFoundException("Secret key not found"));
    }

    @Override
    @Transactional
    @CachePut(cacheNames = "SecretKeyService::getByKey", key = "#result.key")
    public SecretKeyEntity getOrCreate(Long telegramId, SecretKeyTypeEntity.Type type) {
        SecretKeyTypeEntity typeEntity = secretKeyTypeService.getByType(type);
        UserEntity user = userQueriesService.getByTelegramId(telegramId);

        SecretKeyEntity.Id id = new SecretKeyEntity.Id();
        id.setUser(user);
        id.setType(typeEntity);

        Optional<SecretKeyEntity> secretKey = secretKeyRepository.findById(id);

        if (secretKey.isPresent()) {
            return secretKey.get();
        }

        SecretKeyEntity newSecretKey = new SecretKeyEntity();
        newSecretKey.setKey(generateKey());
        newSecretKey.setId(id);

        schedulerRemoveCode.schedule(() -> removeKey(newSecretKey), 10, TimeUnit.MINUTES);

        return secretKeyRepository.save(newSecretKey);
    }

    private void removeKey(SecretKeyEntity secretKey) {
        secretKeyRepository.delete(secretKey);
        Cache cache = cacheManager.getCache("SecretKeyService::getByKey");
        if(cache != null) cache.evict(secretKey.getKey());
    }

    private String generateKey() {
        int code = random.nextInt(100000, 999999);
        return Integer.toString(code);
    }
}
