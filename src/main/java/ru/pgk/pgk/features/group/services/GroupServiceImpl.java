package ru.pgk.pgk.features.group.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pgk.pgk.features.department.entitites.DepartmentEntity;
import ru.pgk.pgk.features.department.services.DepartmentService;
import ru.pgk.pgk.features.group.entities.GroupEntity;
import ru.pgk.pgk.features.group.repositories.GroupRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;

    private final DepartmentService departmentService;

    @Override
    @Transactional
    public GroupEntity add(String name, Short departmentId) {
        Optional<GroupEntity> group = groupRepository.findByNameAndDepartmentId(name, departmentId);
        if(group.isPresent()) return group.get();

        GroupEntity newGroup = new GroupEntity();
        DepartmentEntity department = departmentService.getById(departmentId);
        newGroup.setName(name);
        newGroup.setDepartment(department);
        return groupRepository.save(newGroup);
    }
}
