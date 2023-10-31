package com.oblitus.serviceApp.Modules.Admin;

import com.oblitus.serviceApp.Modules.Admin.DTOs.*;
import com.oblitus.serviceApp.Security.JWT.JWTService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final RuleService ruleService;
    private final RuleMapper ruleMapper;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;
    public AuthResponse register(RUserDTO userDTO) {
        UserDTO user = new UserDTO(null,
                userDTO.email(),
                userDTO.userName(),
                userDTO.name(),
                userDTO.surname(),
                userDTO.password(),
                ruleService.getAll().stream().filter(
                        ruleDTO -> Objects.equals(ruleDTO.getName(), ERule.USER.toString())
                ).map(ruleMapper).collect(Collectors.toList()),
                null
        );
        var userDetails = userService.add(user);
        var token = jwtService.generateToken(userService.get(new UserDTO(userDetails.getUuid())));
        return AuthResponse.builder().token(token).build();
    }
    public AuthResponse login(LUserDTO userDTO){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userDTO.userName(),
                        userDTO.password()
                )
        );
        User optUser = (User)userService.loadUserByUsername(userDTO.userName());
        optUser.setLastLoginDate(LocalDateTime.now());
        var token = jwtService.generateToken(userService.setLastLoginDate(optUser));
        return AuthResponse.builder().token(token).build();

    }
}
