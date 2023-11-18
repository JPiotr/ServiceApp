package com.oblitus.serviceApp.Abstracts;

import java.util.Collection;

public interface ICollectionService<TEntity> {
    Collection<TEntity> addCollection(Collection<TEntity> collection);

    Collection<Boolean> deleteCollection(Collection<TEntity> collection);

}
