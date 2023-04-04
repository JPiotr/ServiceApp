package com.oblitus.serviceApp.Modules.Admin.DTOs;

import com.oblitus.serviceApp.Modules.Admin.Entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserMapper implements Function<User,UserDTO> {
    private final RoleMapper roleMapper;

    @Override
    public UserDTO apply(User user) {
        return new UserDTO(
                user.getUsername(),
                user.getEmail(),
                user.getLastLoginDate(),
                user.getCredentialExpirationDate(),
                user.getCredentialExpirationDate(),
                user.isEnabled(),
                user.isExpired(),
                user.isCredentialsExpired(),
                user.getPassword(),
                user.getRoles().stream().map(roleMapper).collect(Collectors.toList())
        );
    }
}
