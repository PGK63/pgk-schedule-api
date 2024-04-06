package ru.pgk.pgk.features.user.repositoties;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pgk.pgk.features.user.entities.UsernamePasswordEntity;

public interface UsernamePasswordRepository extends JpaRepository<UsernamePasswordEntity, Integer> {}
