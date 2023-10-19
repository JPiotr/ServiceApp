package com.oblitus.serviceApp.Modules.Service.DTOs;

import com.oblitus.serviceApp.Modules.Admin.DTOs.BaseUserResponse;

import java.time.LocalDateTime;
import java.util.UUID;

public record CommentResponse(
        UUID id,
        String content,
        UUID subject,
        BaseUserResponse creator,
        LocalDateTime creationDate,
        LocalDateTime lastModyficationDate
) {
}
