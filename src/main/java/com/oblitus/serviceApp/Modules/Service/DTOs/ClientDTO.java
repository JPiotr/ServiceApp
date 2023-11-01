package com.oblitus.serviceApp.Modules.Service.DTOs;

import java.util.UUID;

public record ClientDTO(
        UUID id,
        String name,
        UUID creator
) {
    public ClientDTO(UUID id) {
        this(id, null, null);
    }
}

