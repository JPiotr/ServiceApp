package com.oblitus.serviceApp.Modules.Service.DTOs;

import java.util.UUID;

public record ActivityDTO(UUID id, String className, String fieldName, String newValue, String oldValue,
                          String activityType, UUID userId, UUID ticketID) {
    public ActivityDTO(UUID id) {
        this(id, null, null, null, null, null, null, null);
    }
}

