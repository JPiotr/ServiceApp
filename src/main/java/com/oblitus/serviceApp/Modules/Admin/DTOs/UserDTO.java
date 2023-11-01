package com.oblitus.serviceApp.Modules.Admin.DTOs;

import com.oblitus.serviceApp.Abstracts.IModelDTO;

import java.util.Collection;
import java.util.UUID;

public record UserDTO(UUID id, String email, String userName, String name, String surname, String password,
                      Collection<RuleDTO> rules, UUID photoId) implements IModelDTO {
    public UserDTO(UUID id) {
        this(id, null, null, null, null, null, null, null);
    }
}

