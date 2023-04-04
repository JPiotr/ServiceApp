package com.oblitus.serviceApp.Modules.Admin.DTOs;

import com.oblitus.serviceApp.Modules.Admin.Entities.Role;
import org.springframework.stereotype.Service;

import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class RoleMapper implements Function<Role,RoleDTO> {
    @Override
    public RoleDTO apply(Role role) {
        return new RoleDTO(
                role.Name,
                role.Modules.stream().map(Enum::toString).collect(Collectors.toList())
        );
    }
}
