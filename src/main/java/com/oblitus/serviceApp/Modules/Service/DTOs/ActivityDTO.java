package com.oblitus.serviceApp.Modules.Service.DTOs;

import java.time.LocalDateTime;
import java.util.UUID;

public record ActivityDTO(
        UUID id,
        String fieldClassName,
        String newValue,
        String oldValue,
        String activityType,
        UUID userId,
        UUID ticketID
) {
}

