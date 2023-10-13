package com.oblitus.serviceApp.Modules.Service.DTOs;

import com.oblitus.serviceApp.Modules.Admin.DTOs.UserDTO;
import com.oblitus.serviceApp.Modules.Service.TicketPriority;
import com.oblitus.serviceApp.Modules.Service.TicketState;

import java.util.Collection;
import java.util.UUID;

public record TicketDTO(
        UUID id,
        String title,
        String description,
        Collection<CommentDTO> comments,
        ClientDTO client,
        UserDTO user,
        TicketState state,
        TicketPriority priority
) {
}
