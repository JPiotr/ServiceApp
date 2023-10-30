package com.oblitus.serviceApp.Modules.Service.DTOs;

import com.oblitus.serviceApp.Modules.Admin.DTOs.BaseUserResponse;

import java.time.LocalDateTime;
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

