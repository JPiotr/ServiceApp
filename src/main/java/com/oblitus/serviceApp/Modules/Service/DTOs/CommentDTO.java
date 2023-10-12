package com.oblitus.serviceApp.Modules.Service.DTOs;

import com.oblitus.serviceApp.Modules.Admin.DTOs.UserDTO;

import java.util.UUID;

public record CommentDTO(
        UUID id,
        String content,
        UserDTO user
) {
}
