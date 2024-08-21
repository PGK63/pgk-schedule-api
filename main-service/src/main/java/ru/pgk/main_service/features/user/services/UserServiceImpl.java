package ru.pgk.main_service.features.user.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pgk.main_service.features.secretKey.entities.SecretKeyEntity;
import ru.pgk.main_service.features.secretKey.services.SecretKeyService;
import ru.pgk.main_service.features.secretKey.services.queries.SecretKeyQueriesService;
import ru.pgk.main_service.features.user.entities.AliceUserEntity;
import ru.pgk.main_service.features.user.entities.UserEntity;
import ru.pgk.main_service.features.user.repositoties.UserRepository;
import ru.pgk.main_service.features.user.services.cache.UserCacheService;
import ru.pgk.main_service.features.user.services.queries.UserQueriesService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    private final UserQueriesService userQueriesService;
    private final UserCacheService userCacheService;

    private final SecretKeyQueriesService secretKeyQueriesService;
    private final SecretKeyService secretKeyService;

    @Override
    @Transactional
    public void setAliceBySecretKey(String aliceId, String key) {
        SecretKeyEntity secretKey = secretKeyQueriesService.getByKey(key);
        UserEntity user = secretKey.getId().getUser();
        setAliceUser(user, aliceId);
        user = userRepository.save(user);
        secretKeyService.deleteByKey(key);
        userCacheService.update(user);
    }

    @Override
    @Transactional
    public void deleteByTelegramId(Long id) {
        UserEntity user = userQueriesService.getByTelegramId(id);
        userRepository.delete(user);
        userCacheService.clearAll(user);
    }

    @Override
    @Transactional
    public void deleteByAliceId(String id) {
        UserEntity user = userQueriesService.getByAliceId(id);
        userRepository.delete(user);
        userCacheService.clearAll(user);
    }

    private void setAliceUser(UserEntity user, String aliceId) {
        AliceUserEntity alice = new AliceUserEntity();
        alice.setAliceId(aliceId);
        alice.setUser(user);
        alice.setId(user.getId());

        user.setAlice(alice);
    }
}
