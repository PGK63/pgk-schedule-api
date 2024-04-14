package ru.pgk.pgk.features.teacher.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pgk.pgk.common.exceptions.ResourceNotFoundException;
import ru.pgk.pgk.features.department.services.DepartmentService;
import ru.pgk.pgk.features.teacher.dto.params.AddOrUpdateTeacherParams;
import ru.pgk.pgk.features.teacher.entities.TeacherEntity;
import ru.pgk.pgk.features.teacher.repositoties.TeacherRepository;

@Service
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;

    private final DepartmentService departmentService;

    @Override
    @Transactional(readOnly = true)
    public Page<TeacherEntity> getAll(String name, Integer offset) {
        if(name != null)
            return teacherRepository.search(name.trim().toLowerCase(), PageRequest.of(offset, 20));
        else
            return teacherRepository.findAll(
                    PageRequest.of(offset, 20, Sort.by("lastName", "firstName", "middleName"))
            );
    }

    @Override
    @Transactional(readOnly = true)
    public TeacherEntity getById(Integer id) {
        return teacherRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public TeacherEntity getByCabinet(String cabinet) {
        return teacherRepository.findByCabinet(cabinet)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher not found"));
    }

    @Override
    @Transactional
    public TeacherEntity add(AddOrUpdateTeacherParams params) {
        TeacherEntity teacher = new TeacherEntity();
        setAddOrUpdateTeacherParams(teacher, params);
        return teacherRepository.save(teacher);
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = "ScheduleSearchService::getAllByTeacherId", key = "#id.toString() + '-' + '*'")
    public TeacherEntity update(Integer id, AddOrUpdateTeacherParams params) {
        TeacherEntity teacher = getById(id);
        setAddOrUpdateTeacherParams(teacher, params);
        return teacherRepository.save(teacher);
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = "ScheduleSearchService::getAllByTeacherId", key = "#id.toString() + '-' + '*'")
    public void deleteById(Integer id) {
        teacherRepository.deleteById(id);
    }

    private void setAddOrUpdateTeacherParams(TeacherEntity teacher, AddOrUpdateTeacherParams params) {
        teacher.setFirstName(params.firstName());
        teacher.setLastName(params.lastName());
        teacher.setMiddleName(params.middleName());
        teacher.setCabinet(params.cabinet());
        teacher.setDepartments(params.departmentIds().stream().map(departmentService::getById).toList());
    }
}
