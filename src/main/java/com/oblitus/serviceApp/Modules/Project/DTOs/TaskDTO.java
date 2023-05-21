package com.oblitus.serviceApp.Modules.Project.DTOs;

import com.oblitus.serviceApp.Modules.Admin.DTOs.UserDTO;
import com.oblitus.serviceApp.Modules.Project.TaskState;
import com.oblitus.serviceApp.Modules.Service.DTOs.CommentDTO;

import java.util.Collection;
import java.util.UUID;

public record TaskDTO(
        UUID id,
        String title,
        String description,
        TaskState taskState,
        UserDTO user,
        Collection<CommentDTO> comments
) {
}
