package com.oblitus.serviceApp.Modules.Admin.DTOs;

import com.oblitus.serviceApp.Abstracts.BaseBuilder;
import com.oblitus.serviceApp.Modules.BaseModule.DTOs.FileResponse;

public class ProfileResponseBuilder extends BaseBuilder<ProfileResponse> {
    @Override
    public void setEntity() {
        entity = new ProfileResponse();
    }

    public ProfileResponseBuilder setUserName(String userName) {
        super.entity.setUserName(userName);
        return this;
    }

    public ProfileResponseBuilder setName(String name) {
        super.entity.setName(name);
        return this;
    }

    public ProfileResponseBuilder setSurname(String surname) {
        super.entity.setSurname(surname);
        return this;
    }

    public ProfileResponseBuilder setAvatar(FileResponse avatar) {
        super.entity.setAvatar(avatar);
        return this;
    }
}
