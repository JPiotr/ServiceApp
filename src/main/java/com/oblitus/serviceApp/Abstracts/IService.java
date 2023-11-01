package com.oblitus.serviceApp.Abstracts;

import java.util.Collection;
import java.util.UUID;

public interface IService<TEntity extends EntityBase, TDtoRecord extends IModelDTO > {
    TEntity get(TDtoRecord dto);
    Collection<TEntity> getAll();
    TEntity update(TDtoRecord dto);
    TEntity add(TDtoRecord dto);
    boolean delete(TDtoRecord dto);
}


