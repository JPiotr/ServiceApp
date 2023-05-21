package com.oblitus.serviceApp.Modules.Project.DTOs;

import java.util.Collection;
import java.util.UUID;

public record FunctionalityDTO(
        UUID id,
        String title,
        int priority,
        int estimate,
        Collection<TaskDTO> tasks
) {
}
