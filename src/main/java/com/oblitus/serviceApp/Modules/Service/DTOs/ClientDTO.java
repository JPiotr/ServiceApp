package com.oblitus.serviceApp.Modules.Service.DTOs;

import com.oblitus.serviceApp.Abstracts.IModelDTO;

import java.util.UUID;

public record ClientDTO(UUID id, String name, UUID creator) implements IModelDTO {
    public ClientDTO(UUID id) {
        this(id, null, null);
    }
}

