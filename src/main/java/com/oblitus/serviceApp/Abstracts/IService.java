package com.oblitus.serviceApp.Abstracts;

import java.util.Collection;

public interface IService<TEntity, TDtoRecord> {
    TEntity get();
    Collection<TEntity> getAll();
    TEntity update(TDtoRecord dto);
    TEntity add(TDtoRecord dto);
    boolean delete(TDtoRecord dto);
}


