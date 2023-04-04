package com.oblitus.serviceApp.Modules.Admin.DTOs;

import java.util.List;

public record RoleDTO(
        String name,
        List<String> modules
) {
}
