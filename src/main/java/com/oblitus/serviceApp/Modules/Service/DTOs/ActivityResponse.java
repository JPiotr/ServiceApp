package com.oblitus.serviceApp.Modules.Service.DTOs;

import java.time.LocalDateTime;
import java.util.UUID;

public record ActivityResponse(
        UUID id,
        String className,
        String fieldName,
        String newValue,
        String oldValue,
        String activityType,
        UUID userId,
        UUID ticketID,
        LocalDateTime creationDate,
        LocalDateTime LastModificationDate
) {
}
