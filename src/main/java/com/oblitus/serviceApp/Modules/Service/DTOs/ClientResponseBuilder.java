package com.oblitus.serviceApp.Modules.Service.DTOs;

import com.oblitus.serviceApp.Abstracts.BaseBuilder;
import com.oblitus.serviceApp.Modules.Admin.DTOs.BaseUserResponse;

public class ClientResponseBuilder extends BaseBuilder<ClientResponse> {
    public ClientResponseBuilder setName(String name){
        entity.setName(name);
        return this;
    }

    public ClientResponseBuilder setCreator(BaseUserResponse creator){
        entity.setCreator(creator);
        return this;
    }
}
