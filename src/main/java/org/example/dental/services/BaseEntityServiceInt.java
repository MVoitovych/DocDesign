package org.example.dental.services;

import java.util.List;
import java.util.Optional;

public interface BaseEntityServiceInt<E, ID> {

    E saveOrUpdate(E e);

    List<E> getAll();

    Optional<E> getById(ID id);

    void deleteById(ID id);
}
