package com.oblitus.serviceApp.Modules.Service.DTOs;

import java.time.LocalDateTime;
import java.util.UUID;

public record ClientResponse(

        UUID id,
        String name,
        LocalDateTime creationDateTime,
        LocalDateTime lastModyficatonDateTime
) {
}
