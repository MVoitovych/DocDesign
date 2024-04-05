package org.example.dental.controllers;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RestControllerInt<E, ID> {

    ResponseEntity<E> save(E e);

    ResponseEntity<E> update(ID i, E e);

    ResponseEntity<List<E>> getAll();

    ResponseEntity<E> getById(ID id);

    ResponseEntity<Void> deleteById(ID id);
}
