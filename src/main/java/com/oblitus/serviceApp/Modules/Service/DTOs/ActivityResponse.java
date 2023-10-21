package com.oblitus.serviceApp.Modules.Service.DTOs;

import com.oblitus.serviceApp.Modules.Admin.DTOs.BaseUserResponse;

import java.time.LocalDateTime;
import java.util.UUID;

public record ActivityResponse(
        UUID id,
        String className,
        String fieldName,
        String newValue,
        String oldValue,
        String activityType,
        BaseUserResponse creator,
        UUID ticketID,
        LocalDateTime creationDate,
        LocalDateTime LastModificationDate
) {
}
