package com.oblitus.serviceApp.Abstracts;

import javax.security.auth.login.AccountLockedException;
import java.util.List;
import java.util.UUID;

/**
 *
 * @param <R> Response record entity
 * @param <C> Creation record entity
 */
public interface DAO<R,C>{
    //C - creation
    //R - response
    R get(UUID id);
    List<R> getAll();
    R save(C c);
    R update(C c) throws AccountLockedException;
    boolean delete(C c);
    boolean delete(UUID id);
}
