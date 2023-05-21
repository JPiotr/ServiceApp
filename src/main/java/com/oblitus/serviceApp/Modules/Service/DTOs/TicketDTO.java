package com.oblitus.serviceApp.Modules.Service.DTOs;

import com.oblitus.serviceApp.Modules.Service.TicketState;

import java.util.Collection;
import java.util.UUID;

public record TicketDTO(
        UUID id,
        String title,
        String description,
        Collection<CommentDTO> comments,
        ClientDTO client,
        TicketState state
) {
}
