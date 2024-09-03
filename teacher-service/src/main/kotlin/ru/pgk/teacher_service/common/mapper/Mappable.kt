package ru.pgk.teacher_service.common.mapper

interface Mappable<E, D> {
    fun toDto(entity: E): D
    fun toDto(entity: Collection<E>): List<D>
    fun toEntity(dto: D): E
    fun toEntity(dto: Collection<D>): List<E>
}