package com.oblitus.serviceApp.Modules.Admin.DTOs;

import java.util.List;
import java.util.UUID;

public record RuleDTO(
        UUID id,
        String name,
        List<String> modules
) {
}
