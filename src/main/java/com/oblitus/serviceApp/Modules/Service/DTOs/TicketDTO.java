package com.oblitus.serviceApp.Modules.Service.DTOs;

import com.oblitus.serviceApp.Abstracts.IModelDTO;
import com.oblitus.serviceApp.Modules.Service.TicketPriority;
import com.oblitus.serviceApp.Modules.Service.TicketState;

import java.util.Collection;
import java.util.UUID;

public record TicketDTO(UUID id, String title, String description, UUID client, UUID assigned, TicketState state,
                        TicketPriority priority, UUID creator, String note, UUID editing, Collection<UUID> files) implements IModelDTO {
    public TicketDTO(UUID id) {
        this(id, null, null, null, null, null, null, null, null, null, null);
    }
}

