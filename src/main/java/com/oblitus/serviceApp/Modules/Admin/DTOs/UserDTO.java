package com.oblitus.serviceApp.Modules.Admin.DTOs;

import java.time.LocalDateTime;
import java.util.Collection;

public record UserDTO(
      String username,
      String email,
      LocalDateTime lastLoginDateTime,
      LocalDateTime credentialExpireDate,
      LocalDateTime accountExpireDate,
      boolean isEnabled,
      boolean isExpired,
      boolean isCredentialExpired,
      String password,
      Collection<RoleDTO> roles
) {
}
