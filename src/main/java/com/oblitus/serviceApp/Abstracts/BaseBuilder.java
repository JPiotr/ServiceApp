package com.oblitus.serviceApp.Abstracts;

import java.time.LocalDateTime;
import java.util.UUID;

public abstract class BaseBuilder<T extends BaseResponse> implements IBuilder{
    protected T entity;

    @Override
    public abstract void setEntity();
    /**
     * Build T class object with properties
     * @return T extends BaseResponse
     * @see BaseResponse
     */
    public T build(){
        return entity;
    }

    public BaseBuilder<T> setUUID(UUID uuid){
        entity.setUuid(uuid);
        return this;
    }

    public BaseBuilder<T> setId(long id){
        entity.setId(id);
        return this;
    }

    public BaseBuilder<T> setCreationDate(LocalDateTime date){
        entity.setCreationDate(date);
        return this;
    }

    public BaseBuilder<T> setLastModificationDate(LocalDateTime date){
        entity.setLastModificationDate(date);
        return this;
    }
}
