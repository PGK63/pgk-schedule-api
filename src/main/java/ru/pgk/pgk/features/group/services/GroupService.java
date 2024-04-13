package ru.pgk.pgk.features.group.services;

import org.springframework.data.domain.Page;
import ru.pgk.pgk.features.group.entities.GroupEntity;

public interface GroupService {

    Page<GroupEntity> getAll(String name, Integer offset);

    GroupEntity add(String name, Short departmentId);
}
