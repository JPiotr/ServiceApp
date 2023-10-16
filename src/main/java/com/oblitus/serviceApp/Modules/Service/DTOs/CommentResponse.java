package com.oblitus.serviceApp.Modules.Service.DTOs;

import java.time.LocalDateTime;
import java.util.UUID;

public record CommentResponse(
        UUID id,
        String content,
        UUID subject,
        UUID creator,
        LocalDateTime creationDate,
        LocalDateTime lastModyficationDate
) {
}
