package com.oblitus.serviceApp.Common.File.DTOs;

import org.springframework.core.io.Resource;

import java.time.LocalDateTime;
import java.util.UUID;

public record FileResponse(
        UUID id,
        UUID objectId,
        String name,
        String fileExtension,
        String fileType,
        LocalDateTime creationDate,
        LocalDateTime lastModificationDate,
        String url,
        String description
) {
}
