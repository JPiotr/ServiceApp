package com.oblitus.serviceApp.Modules.Admin.DTOs;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;

public record UserResponse(
        UUID id,
        String userName,
        String email,
        String name,
        String surname,
        Collection<RuleDTO> rules,
        LocalDateTime lastLoginDate,
        LocalDateTime credentialExpirationDate,
        LocalDateTime accountExpirationDate,
        boolean isEnabled,
        boolean isExpired,
        boolean isCredentialsExpired,
        LocalDateTime creationDate,
        LocalDateTime lastModificationDate
) {}
