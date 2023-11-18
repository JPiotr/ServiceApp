package com.oblitus.serviceApp.Modules.Service.DTOs;

import com.oblitus.serviceApp.Abstracts.IModelDTO;

import java.util.UUID;

public record ActivityDTO(UUID id, String className, String fieldName, String newValue, String oldValue,
                          String activityType, UUID userId, UUID ticketID) implements IModelDTO {
    public ActivityDTO(UUID id) {
        this(id, null, null, null, null, null, null, null);
    }
}

