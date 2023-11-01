package com.oblitus.serviceApp.Modules.Admin.DTOs;

import com.oblitus.serviceApp.Abstracts.BaseBuilder;
import com.oblitus.serviceApp.Modules.BaseModule.DTOs.FileResponse;

import java.time.LocalDateTime;

public class MyProfileResponseBuilder extends BaseBuilder<MyProfileResponse> {
    @Override
    public void setEntity() {
        entity = new MyProfileResponse();
    }

    public MyProfileResponseBuilder setEmail(String email) {
        super.entity.setEmail(email);
        return this;
    }

    public MyProfileResponseBuilder setLastLoginDate(LocalDateTime lastLoginDate) {
        super.entity.setLastLoginDate(lastLoginDate);
        return this;
    }

    public MyProfileResponseBuilder setCredentialExpirationDate(LocalDateTime credentialExpirationDate) {
        super.entity.setCredentialExpirationDate(credentialExpirationDate);
        return this;
    }

    public MyProfileResponseBuilder setAccountExpirationDate(LocalDateTime accountExpirationDate) {
        super.entity.setAccountExpirationDate(accountExpirationDate);
        return this;
    }

    public MyProfileResponseBuilder setUserName(String userName) {
        super.entity.setUserName(userName);
        return this;
    }

    public MyProfileResponseBuilder setName(String name) {
        super.entity.setName(name);
        return this;
    }

    public MyProfileResponseBuilder setSurname(String surname) {
        super.entity.setSurname(surname);
        return this;
    }

    public MyProfileResponseBuilder setAvatar(FileResponse avatar) {
        super.entity.setAvatar(avatar);
        return this;
    }

}
