package com.oblitus.serviceApp.Modules.BaseModule.Responses;

import com.oblitus.serviceApp.Abstracts.BaseBuilder;
import com.oblitus.serviceApp.Modules.Admin.Responses.ProfileResponse;

public class NotificationResponseBuilder extends BaseBuilder<NotificationResponse> {
    @Override
    public void setEntity() {
        this.entity = new NotificationResponse();
    }

    public NotificationResponseBuilder setUser(ProfileResponse user){
        this.entity.setUser(user);
        return this;
    }

    public NotificationResponseBuilder setApp(boolean app){
        this.entity.setApp(app);
        return this;
    }

    public NotificationResponseBuilder setEmail(boolean email){
        this.entity.setEmail(email);
        return this;
    }

    public NotificationResponseBuilder setType(String type){
        this.entity.setType(type);
        return this;
    }
}
