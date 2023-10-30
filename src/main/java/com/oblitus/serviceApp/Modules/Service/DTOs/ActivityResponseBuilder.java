package com.oblitus.serviceApp.Modules.Service.DTOs;

import com.oblitus.serviceApp.Abstracts.BaseBuilder;
import com.oblitus.serviceApp.Modules.Admin.DTOs.BaseUserResponse;

import java.util.UUID;

public class ActivityResponseBuilder extends BaseBuilder<ActivityResponse> {

    public ActivityResponseBuilder setClassName(String className){
        this.entity.setClassName(className);
        return this;
    }
    public ActivityResponseBuilder setFieldName(String fieldName){
        this.entity.setFieldName(fieldName);
        return this;
    }
    public ActivityResponseBuilder setNewValue(String newValue){
        this.entity.setNewValue(newValue);
        return this;
    }
    public ActivityResponseBuilder setOldValue(String oldValue){
        this.entity.setOldValue(oldValue);
        return this;
    }
    public ActivityResponseBuilder setActivityType(String activityType){
        this.entity.setActivityType(activityType);
        return this;
    }
    public ActivityResponseBuilder setCreator(BaseUserResponse creator){
        this.entity.setCreator(creator);
        return this;
    }
    public ActivityResponseBuilder setObjectUuid(UUID objectUuid){
        this.entity.setUuid(objectUuid);
        return this;
    }
}
