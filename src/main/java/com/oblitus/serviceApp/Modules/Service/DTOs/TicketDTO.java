package com.oblitus.serviceApp.Modules.Service.DTOs;

import java.util.Collection;
import java.util.UUID;

public record TicketDTO(
        UUID id,
        String title,
        String description,
        Collection<CommentDTO> comments,
        ClientDTO client
) {
}
