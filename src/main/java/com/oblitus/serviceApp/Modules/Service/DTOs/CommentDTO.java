package com.oblitus.serviceApp.Modules.Service.DTOs;

import java.util.UUID;

public record CommentDTO(
        UUID id,
        String content,
        UUID subject,
        UUID creator
) {
}

