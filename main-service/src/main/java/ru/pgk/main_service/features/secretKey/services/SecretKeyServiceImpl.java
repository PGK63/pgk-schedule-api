package ru.pgk.main_service.features.secretKey.services;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pgk.main_service.features.secretKey.entities.SecretKeyEntity;
import ru.pgk.main_service.features.secretKey.entities.SecretKeyTypeEntity;
import ru.pgk.main_service.features.secretKey.repositories.SecretKeyRepository;
import ru.pgk.main_service.features.secretKey.services.queries.SecretKeyQueriesService;
import ru.pgk.main_service.features.secretKey.services.type.SecretKeyTypeService;
import ru.pgk.main_service.features.user.entities.UserEntity;
import ru.pgk.main_service.features.user.services.queries.UserQueriesService;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class SecretKeyServiceImpl implements SecretKeyService {

    private final SecretKeyRepository secretKeyRepository;

    private final SecretKeyQueriesService secretKeyQueriesService;
    private final SecretKeyTypeService secretKeyTypeService;
    private final UserQueriesService userQueriesService;

    private final CacheManager cacheManager;

    private final Random random = new Random();
    private final ScheduledExecutorService schedulerRemoveCode = Executors.newScheduledThreadPool(1);

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
            LocalDateTime currentDate = LocalDateTime.now();
            LocalDateTime date = secretKey.get().getDate();
            LocalDateTime tenMinutesAgo = currentDate.minusMinutes(10);

            if (date.isAfter(tenMinutesAgo))
                return secretKey.get();

            removeKey(secretKey.get());
        }

        return creatKey(id);
    }

    @Override
    public void deleteByKey(String key) {
        SecretKeyEntity secretKey = secretKeyQueriesService.getByKey(key);
        removeKey(secretKey);
    }

    private SecretKeyEntity creatKey(SecretKeyEntity.Id id) {
        SecretKeyEntity secretKey = new SecretKeyEntity();
        secretKey.setKey(generateKey());
        secretKey.setId(id);

        schedulerRemoveCode.schedule(() -> removeKey(secretKey), 10, TimeUnit.MINUTES);

        return secretKeyRepository.save(secretKey);
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
