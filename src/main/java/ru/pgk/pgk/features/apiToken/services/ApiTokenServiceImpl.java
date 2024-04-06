package ru.pgk.pgk.features.apiToken.services;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pgk.pgk.common.exceptions.ResourceNotFoundException;
import ru.pgk.pgk.features.apiToken.entities.ApiTokenEntity;
import ru.pgk.pgk.features.apiToken.repositories.ApiTokenRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ApiTokenServiceImpl implements ApiTokenService {

    private final ApiTokenRepository apiTokenRepository;

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "ApiTokenService::getAll")
    public List<ApiTokenEntity> getAll() {
        return apiTokenRepository.findAll();
    }

    @Transactional(readOnly = true)
    public ApiTokenEntity getById(Integer id) {
        return apiTokenRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Api token not found"));
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "ApiTokenService::getByToken", key = "#token")
    public ApiTokenEntity getByToken(UUID token) {
        return apiTokenRepository.findByToken(token)
                .orElseThrow(() -> new ResourceNotFoundException("Api token not found"));
    }

    @Override
    @Transactional
    @CachePut(cacheNames = "ApiTokenService::getByToken", key = "#result.token")
    @CacheEvict(cacheNames = "ApiTokenService::getAll", allEntries = true)
    public ApiTokenEntity add() {
        ApiTokenEntity apiToken = new ApiTokenEntity();
        return apiTokenRepository.save(apiToken);
    }

    @Override
    @Transactional
    @Caching(
            evict = {
                    @CacheEvict(cacheNames = "ApiTokenService::getByToken", key = "#result.token"),
                    @CacheEvict(cacheNames = "ApiTokenService::getAll", allEntries = true)
            }
    )
    public ApiTokenEntity deleteById(Integer id) {
        ApiTokenEntity apiToken = getById(id);
        apiTokenRepository.delete(apiToken);
        return apiToken;
    }
}
