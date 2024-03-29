package com.oblitus.serviceApp.Modules.Admin.Responses;

import com.oblitus.serviceApp.Abstracts.BaseBuilder;
import com.oblitus.serviceApp.Modules.Admin.DTOs.RuleDTO;
import com.oblitus.serviceApp.Modules.BaseModule.Responses.FileResponse;

import java.time.LocalDateTime;
import java.util.Collection;

class UserResponseBuilder extends BaseBuilder<UserResponse> {
    @Override
    public void setEntity() {
        entity = new UserResponse();
    }

    public UserResponseBuilder setName(String name){
        super.entity.setName(name);
        return this;
    }
    public UserResponseBuilder setEmail(String email){
        super.entity.setEmail(email);
        return this;
    }
    public UserResponseBuilder setSurname(String surname){
        super.entity.setSurname(surname);
        return this;
    }
    public UserResponseBuilder setRules(Collection<RuleDTO> rules){
        super.entity.setRules(rules);
        return this;
    }
    public UserResponseBuilder setLastLoginDate(LocalDateTime lastLoginDate){
        super.entity.setLastLoginDate(lastLoginDate);
        return this;
    }
    public UserResponseBuilder setCredentialExpirationDate(LocalDateTime credentialExpirationDate){
        super.entity.setCredentialExpirationDate(credentialExpirationDate);
        return this;
    }
    public UserResponseBuilder setAccountExpirationDate(LocalDateTime accountExpirationDate){
        super.entity.setAccountExpirationDate(accountExpirationDate);
        return this;
    }
    public UserResponseBuilder setIsEnabled(boolean isEnabled){
        super.entity.setEnabled(isEnabled);
        return this;
    }
    public UserResponseBuilder setIsExpired(boolean isExpired){
        super.entity.setExpired(isExpired);
        return this;
    }
    public UserResponseBuilder setIsCredentialExpired(boolean isCredentialsExpired){
        super.entity.setCredentialsExpired(isCredentialsExpired);
        return this;
    }
    public UserResponseBuilder setAvatar(FileResponse file){
        super.entity.setAvatar(file);
        return this;
    }
}
