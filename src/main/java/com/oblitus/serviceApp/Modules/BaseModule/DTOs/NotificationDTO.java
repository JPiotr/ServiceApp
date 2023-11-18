package com.oblitus.serviceApp.Modules.BaseModule.DTOs;

import com.oblitus.serviceApp.Abstracts.IModelDTO;

import java.util.UUID;

public record NotificationDTO(
        UUID id,
        String type,
        Boolean app,
        Boolean email,
        UUID user

) implements IModelDTO {
    public NotificationDTO(UUID id) {
        this(id, null, null, null, null);
    }
}
