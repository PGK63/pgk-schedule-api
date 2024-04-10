package ru.pgk.pgk.features.group.services;

import ru.pgk.pgk.features.group.entities.GroupEntity;

public interface GroupService {

    GroupEntity add(String name, Short departmentId);
}
