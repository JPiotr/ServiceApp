package com.oblitus.serviceApp.Modules.Admin.DTOs;

import com.oblitus.serviceApp.Abstracts.BaseBuilder;
import com.oblitus.serviceApp.Common.File.DTOs.FileResponse;


public class BaseUserResponseBuilder extends BaseBuilder<BaseUserResponse> {
    @Override
    public void setEntity() {
        entity = new BaseUserResponse();
    }

    public BaseUserResponseBuilder setUserName(String userName){
        super.entity.setUserName(userName);
        return this;
    }

    public BaseUserResponseBuilder setName(String name){
        super.entity.setName(name);
        return this;
    }
    public BaseUserResponseBuilder setSurname(String surname){
        super.entity.setSurname(surname);
        return this;
    }
    public BaseUserResponseBuilder setPhoto(FileResponse photo){
        super.entity.setPhoto(photo);
        return this;
    }

}
