package ru.pgk.pgk.features.user.services;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pgk.pgk.common.exceptions.ResourceNotFoundException;
import ru.pgk.pgk.features.user.entities.UserEntity;
import ru.pgk.pgk.features.user.repositoties.UserRepository;

import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "UserService::getById", key = "#id")
    public UserEntity getById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "UserService::getByTelegramId", key = "#id")
    public UserEntity getByTelegramId(Long id) {
        return userRepository.findByTelegramId(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "UserService::getByAliceId", key = "#id")
    public UserEntity getByAliceId(String id) {
        return userRepository.findByAliceId(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "UserService::existByTelegramId", key = "#id")
    public Boolean existByTelegramId(Long id) {
        return exist(() -> getByTelegramId(id));
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "UserService::existByAliceId", key = "#id")
    public Boolean existByAliceId(String id) {
        return exist(() -> getByAliceId(id));
    }

    private Boolean exist(Supplier<?> exceptionSupplier) {
        try {
            exceptionSupplier.get();
            return true;
        }catch (ResourceNotFoundException ignore) {
            return false;
        }
    }
}
