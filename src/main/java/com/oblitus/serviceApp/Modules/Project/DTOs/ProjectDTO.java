package com.oblitus.serviceApp.Modules.Project.DTOs;

import com.oblitus.serviceApp.Modules.Admin.DTOs.UserDTO;

import java.util.Collection;
import java.util.UUID;

public record ProjectDTO(
        UUID id,
        String name,
        String description,
        Collection<FunctionalityDTO> functionalities,
        UserDTO owner

) {
}
