package ru.pgk.teacher_service.features.services

import jakarta.persistence.EntityManager
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Caching
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.pgk.teacher_service.features.dto.params.AddOrUpdateTeacherParams
import ru.pgk.teacher_service.features.entities.DepartmentEntity
import ru.pgk.teacher_service.features.entities.TeacherEntity
import ru.pgk.teacher_service.features.repositories.TeacherRepository
import ru.pgk.teacher_service.features.services.queries.TeacherQueriesService

@Service
class TeacherServiceImpl(
    private val teacherRepository: TeacherRepository,
    private val teacherQueriesService: TeacherQueriesService,
    private val entityManager: EntityManager
) : TeacherService {

    @Transactional
    @Caching(
        put = [
            CachePut(
                cacheNames = ["TeacherQueriesService::getById"],
                key = "#result.id"
            ),
            CachePut(
                cacheNames = ["TeacherQueriesService::getByCabinet"],
                key = "#result.cabinet",
                condition = "#result.cabinet != null",
                unless = ""
            )
        ],
        evict = [
            CacheEvict(
                cacheNames = ["TeacherQueriesService::getAllSearchByName"],
                allEntries = true
            ),
            CacheEvict(
                cacheNames = ["TeacherQueriesService::getAll"],
                allEntries = true
            )
        ]
    )
    override fun add(params: AddOrUpdateTeacherParams): TeacherEntity {
        val teacher = TeacherEntity()
        setAddOrUpdateTeacherParams(teacher, params)
        return teacherRepository.save(teacher)
    }

    @Transactional
    @Caching(
        put = [
            CachePut(
                cacheNames = ["TeacherQueriesService::getById"],
                key = "#result.id"
            ),
            CachePut(
                cacheNames = ["TeacherQueriesService::getByCabinet"],
                key = "#result.cabinet",
                condition = "#result.cabinet != null"
            )
        ],
        evict = [
            CacheEvict(
                cacheNames = ["ScheduleSearchService::getAllByTeacherId"],
                key = "#id.toString() + '-' + '*'"
            ),
            CacheEvict(
                cacheNames = ["TeacherQueriesService::getAllSearchByName"],
                allEntries = true
            ),
            CacheEvict(
                cacheNames = ["TeacherQueriesService::getAll"],
                allEntries = true
            ),
            CacheEvict(
                cacheNames = ["ScheduleService::teacherGetByTeacherId"],
                key = "'*' + '-' + #id"
            )
        ]
    )
    override fun update(id: Int, params: AddOrUpdateTeacherParams): TeacherEntity {
        val teacher: TeacherEntity = teacherQueriesService.getById(id)
        setAddOrUpdateTeacherParams(teacher, params)
        return teacherRepository.save(teacher)
    }

    @Transactional
    @Caching(
        evict = [
            CacheEvict(
                cacheNames = ["TeacherQueriesService::getById"],
                key = "#result.id"
            ),
            CacheEvict(
                cacheNames = ["TeacherQueriesService::getByCabinet"],
                key = "#result.cabinet",
                condition = "#result.cabinet != null "
            ),
            CacheEvict(
                cacheNames = ["TeacherQueriesService::getAllSearchByName"],
                allEntries = true
            ),
            CacheEvict(
                cacheNames = ["TeacherQueriesService::getAll"],
                allEntries = true
            ),
            CacheEvict(
                cacheNames = ["ScheduleSearchService::getAllByTeacherId"],
                key = "#id.toString() + '-' + '*'"
            ),
            CacheEvict(
                cacheNames = ["ScheduleService::teacherGetByTeacherId"],
                key = "'*' + '-' + #id"
            )
        ]
    )
    override fun deleteById(id: Int): TeacherEntity {
        val teacher: TeacherEntity = teacherQueriesService.getById(id)
        teacherRepository.delete(teacher)
        return teacher
    }

    private fun setAddOrUpdateTeacherParams(teacher: TeacherEntity, params: AddOrUpdateTeacherParams) {
        teacher.firstName = params.firstName
        teacher.lastName = params.lastName
        teacher.middleName = params.middleName
        teacher.cabinet = params.cabinet
        teacher.departments = params.departmentIds.map { id ->
            entityManager.getReference(DepartmentEntity::class.java, id)
        }
    }
}