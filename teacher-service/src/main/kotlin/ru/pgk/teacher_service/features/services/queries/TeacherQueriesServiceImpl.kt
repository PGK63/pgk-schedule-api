package ru.pgk.teacher_service.features.services.queries

import org.springframework.cache.annotation.Cacheable
import org.springframework.cache.annotation.Caching
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.pgk.teacher_service.common.exceptions.ResourceNotFoundException
import ru.pgk.teacher_service.features.entities.TeacherEntity
import ru.pgk.teacher_service.features.repositories.TeacherRepository
import java.util.*

@Service
class TeacherQueriesServiceImpl(
    private val teacherRepository: TeacherRepository
): TeacherQueriesService {

    private val pageSize = 20

    @Transactional(readOnly = true)
    @Caching(
        cacheable = [Cacheable(
            cacheNames = ["TeacherQueriesService::getAllSearchByName"],
            condition = "#name != null",
            key = "#name.toString() + '-' + #offset"
        ), Cacheable(cacheNames = ["TeacherQueriesService::getAll"], condition = "#name == null", key = "#offset")]
    )
    override fun getAll(name: String?, offset: Int): Page<TeacherEntity> {
        return if(name.isNullOrEmpty())
            teacherRepository.findAll(
                PageRequest.of(offset, pageSize, Sort.by("lastName", "firstName", "middleName"))
            )
        else
            teacherRepository.search(
                name = name.trim().lowercase(Locale.getDefault()),
                pageable = PageRequest.of(offset, pageSize)
            )
    }

    @Transactional(readOnly = true)
    @Cacheable(cacheNames = ["TeacherQueriesService::getById"], key = "#id")
    override fun getById(id: Int): TeacherEntity {
        return teacherRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Teacher not found") }
    }

    @Transactional(readOnly = true)
    @Cacheable(cacheNames = ["TeacherQueriesService::getByCabinet"], key = "#cabinet.toString()")
    override fun getByCabinet(cabinet: String): TeacherEntity {
        return teacherRepository.findByCabinet(cabinet)
            .orElseThrow { ResourceNotFoundException("Teacher not found") }
    }
}