package com.oblitus.serviceApp.Abstracts;

import java.util.Collection;

public interface ICollectionService<TEntity, TDtoRecord> {
    Collection<TEntity> addCollection(Collection<TDtoRecord> dtoRecords);

    Collection<Boolean> deleteCollection(Collection<TDtoRecord> tDtoRecords);

}
