package com.oblitus.serviceApp.Abstracts;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DAO<T> {
    Optional<T> get(UUID id);

    List<T> getAll();

    T save(T t);

    T update(T t);

    boolean delete(T t);
}
