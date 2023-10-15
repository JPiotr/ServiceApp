package com.oblitus.serviceApp.Abstracts;

import javax.security.auth.login.AccountLockedException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DAO<R,C> {
    //C - creation
    //R - response
    Optional<R> get(UUID id);

    List<R> getAll();

    R save(C c);

    R update(C c) throws AccountLockedException;

    boolean delete(C c);
    boolean delete(UUID id);
}
