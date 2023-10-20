package com.oblitus.serviceApp.Modules.Admin.DTOs;

import java.util.UUID;

public record UserDTO(
      UUID id,
      String email,
      String userName,
      String name,
      String surname,
      String password
){}

