package com.oblitus.serviceApp.Modules.Admin;

import com.oblitus.serviceApp.Modules.Admin.DTOs.*;
import com.oblitus.serviceApp.Modules.ModulesWrapper;
import com.oblitus.serviceApp.Security.jwt.JWTService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final RuleService ruleService;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;
    public AuthResponse register(RUserDTO userDTO){
        UserDTO user = new UserDTO(null,
                userDTO.name(),
                userDTO.surname(),
                userDTO.email(),
                passwordEncoder.encode(userDTO.password()),
                null
//                modulesWrapper.adminModule.getAdminDAO().getRuleDao().getAll()
//                        .stream().filter(
//                                ruleDTO -> ruleDTO.name() == ERule.USER.toString()
//                        ).collect(Collectors.toList())
        );
        var userDetails = userService.addUser(
                user.name(),
                user.email(),
                ruleService.getAllRoles().stream().filter(
                        ruleDTO -> ruleDTO.getName() == ERule.USER.toString()
                ).collect(Collectors.toList()),
                user.password(),
                user.userName(),
                user.surname()
        );
        var optUser = userService.getUser(userDetails.getID());
        if(optUser.isPresent()){
            var token = jwtService.generateToken(optUser.get());
            return AuthResponse.builder().token(token).build();
        }
        return AuthResponse.builder().build();
    }

    public AuthResponse login(LUserDTO userDTO){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userDTO.userName(),
                        userDTO.password()
                )
        );
        var optUser = userService.getUser(userDTO.userName());
        if(optUser.isPresent()){
            var token = jwtService.generateToken(optUser.get());
            return AuthResponse.builder().token(token).build();
        }
        return AuthResponse.builder().build();

    }
}
