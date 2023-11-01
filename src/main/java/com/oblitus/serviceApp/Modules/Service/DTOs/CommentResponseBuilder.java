package com.oblitus.serviceApp.Modules.Service.DTOs;

import com.oblitus.serviceApp.Abstracts.BaseBuilder;
import com.oblitus.serviceApp.Modules.Admin.Responses.ProfileResponse;

import java.util.UUID;

public class CommentResponseBuilder extends BaseBuilder<CommentResponse> {
    @Override
    public void setEntity() {
        entity = new CommentResponse();
    }

    public CommentResponseBuilder setContent(String content) {
        entity.setContent(content);
        return this;
    }

    public CommentResponseBuilder setSubject(UUID subject) {
        entity.setSubject(subject);
        return this;
    }

    public CommentResponseBuilder setCreator(ProfileResponse creator) {
        entity.setCreator(creator);
        return this;
    }
}
