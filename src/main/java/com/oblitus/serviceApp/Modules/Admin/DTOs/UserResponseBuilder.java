package com.oblitus.serviceApp.Modules.Admin.DTOs;

import com.oblitus.serviceApp.Abstracts.BaseBuilder;
import com.oblitus.serviceApp.Modules.BaseModule.DTOs.FileResponse;

import java.time.LocalDateTime;
import java.util.Collection;

public class UserResponseBuilder extends BaseBuilder<UserResponse> {
    @Override
    public void setEntity() {
        entity = new UserResponse();
    }

    public UserResponseBuilder setUserName(String userName){
        super.entity.setUserName(userName);
        return this;
    }
    public UserResponseBuilder setName(String name){
        super.entity.setName(name);
        return this;
    }
    public UserResponseBuilder setEmail(String email){
        super.entity.setEmail(email);
        return this;
    }public UserResponseBuilder setSurname(String surname){
        super.entity.setSurname(surname);
        return this;
    }
    public UserResponseBuilder setRules(Collection<RuleDTO> rules){
        super.entity.setRules(rules);
        return this;
    }public UserResponseBuilder setLastLoginDate(LocalDateTime lastLoginDate){
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
    public UserResponseBuilder setFile(FileResponse file){
        super.entity.setFile(file);
        return this;
    }
}
