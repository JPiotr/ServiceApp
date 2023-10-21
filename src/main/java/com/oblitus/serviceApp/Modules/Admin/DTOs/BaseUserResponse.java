package com.oblitus.serviceApp.Modules.Admin.DTOs;

import com.oblitus.serviceApp.Common.File.DTOs.FileResponse;

import java.util.UUID;

public record BaseUserResponse(
        UUID id,
        String userName,
        String name,
        String surname,
        FileResponse photo
) {
}
