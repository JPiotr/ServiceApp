package com.oblitus.serviceApp.Modules.Service.DTOs;

import java.util.UUID;

public record CommentDTO(UUID id, String content, UUID subject, UUID creator) {
    public CommentDTO(UUID id) {
        this(id, null, null, null);
    }
}

