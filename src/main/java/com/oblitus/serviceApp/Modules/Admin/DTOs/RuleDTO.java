package com.oblitus.serviceApp.Modules.Admin.DTOs;

import java.util.List;
import java.util.UUID;
import com.oblitus.serviceApp.Modules.Module;

public record RuleDTO(
        UUID id,
        String name,
        List<Module> modules
) {
}
