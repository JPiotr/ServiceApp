package com.oblitus.serviceApp.Modules.Service.DTOs;

import com.oblitus.serviceApp.Abstracts.IModelDTO;

import java.util.UUID;

public record CommentDTO(UUID id, String content, UUID subject, UUID creator) implements IModelDTO {
    public CommentDTO(UUID id) {
        this(id, null, null, null);
    }
}

