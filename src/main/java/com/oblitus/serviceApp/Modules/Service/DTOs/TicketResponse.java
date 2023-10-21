package com.oblitus.serviceApp.Modules.Service.DTOs;

import com.oblitus.serviceApp.Common.File.DTOs.FileResponse;
import com.oblitus.serviceApp.Modules.Admin.DTOs.BaseUserResponse;
import com.oblitus.serviceApp.Modules.Service.TicketPriority;
import com.oblitus.serviceApp.Modules.Service.TicketState;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;

public record TicketResponse(
        UUID id,
        String title,
        String description,
        UUID client,
        BaseUserResponse assigned,
        TicketState state,
        TicketPriority priority,
        LocalDateTime creationDate,
        LocalDateTime LastModificationDate,
        BaseUserResponse creator,
        int number,
        String note,
        Collection<FileResponse> files
) {
}
