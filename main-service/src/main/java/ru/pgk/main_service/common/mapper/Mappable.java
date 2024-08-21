package ru.pgk.main_service.common.mapper;

import java.util.Collection;
import java.util.List;

public interface Mappable<E, D> {

    D toDto(E entity);

    List<D> toDto(Collection<E> entity);

    E toEntity(D dto);

    Collection<E> toEntity(Collection<D> dtos);

}
