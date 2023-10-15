package com.oblitus.serviceApp.Modules.Service.DTOs;

import java.time.LocalDateTime;
import java.util.UUID;

public record ClientDTO(
        UUID id,
        String name
) {
}

