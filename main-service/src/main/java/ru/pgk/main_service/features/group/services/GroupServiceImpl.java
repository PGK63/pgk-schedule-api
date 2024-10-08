package ru.pgk.main_service.features.group.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pgk.main_service.features.department.entitites.DepartmentEntity;
import ru.pgk.main_service.features.department.services.DepartmentService;
import ru.pgk.main_service.features.group.entities.GroupEntity;
import ru.pgk.main_service.features.group.repositories.GroupRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;

    private final DepartmentService departmentService;

    @Value("${base.page_size}")
    private Integer pageSize;

    @Override
    @Transactional(readOnly = true)
    public Page<GroupEntity> getAll(String name, Integer offset) {
        return groupRepository.search(name, PageRequest.of(offset, pageSize));
    }

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
