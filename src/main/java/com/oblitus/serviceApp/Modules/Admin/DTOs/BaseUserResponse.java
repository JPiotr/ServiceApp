package com.oblitus.serviceApp.Modules.Admin.DTOs;

import java.util.UUID;

public record BaseUserResponse(
        UUID id,
        String userName,
        String name,
        String surname
) {
}
