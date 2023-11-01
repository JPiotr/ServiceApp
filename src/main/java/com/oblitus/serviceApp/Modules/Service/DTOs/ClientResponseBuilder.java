package com.oblitus.serviceApp.Modules.Service.DTOs;

import com.oblitus.serviceApp.Abstracts.BaseBuilder;
import com.oblitus.serviceApp.Modules.Admin.Responses.ProfileResponse;

public class ClientResponseBuilder extends BaseBuilder<ClientResponse> {
    @Override
    public void setEntity() {
        entity = new ClientResponse();
    }

    public ClientResponseBuilder setName(String name) {
        entity.setName(name);
        return this;
    }

    public ClientResponseBuilder setCreator(ProfileResponse creator) {
        entity.setCreator(creator);
        return this;
    }
}
