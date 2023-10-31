package com.oblitus.serviceApp.Modules.Admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oblitus.serviceApp.Modules.Admin.DTOs.*;
import com.oblitus.serviceApp.Security.JWT.JWTService;
import com.oblitus.serviceApp.Security.Token;
import com.oblitus.serviceApp.Security.TokenRepository;
import com.oblitus.serviceApp.Security.TokenType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
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
                passwordEncoder.encode(userDTO.password()),
                ruleService.getAll().stream().filter(
                        ruleDTO -> Objects.equals(ruleDTO.getName(), ERule.USER.toString())
                ).map(ruleMapper).collect(Collectors.toList()),
                null
        );
        User usr = new User(user.userName(),user.name(),user.surname(),user.email(),
                ruleService.getAll().stream().filter(
                        ruleDTO -> Objects.equals(ruleDTO.getName(), ERule.USER.toString())
                ).collect(Collectors.toList())
                ,user.password());

        var userDetails = userRepository.save(usr);
        var token = jwtService.generateToken(usr);
        var refToken = jwtService.generateRefreshToken(usr);
        saveToken(userDetails,token);
        return AuthResponse.builder()
                .accessToken(token)
                .refreshToken(refToken)
                .build();
    }
    public AuthResponse login(LUserDTO userDTO){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userDTO.userName(),
                        userDTO.password()
                )
        );
        User optUser = userRepository.findByUsername(userDTO.userName()).orElseThrow();
        optUser.setLastLoginDate(LocalDateTime.now());
        optUser.setPassword(passwordEncoder.encode(userDTO.password()));
        var token = jwtService.generateToken(userRepository.save(optUser));
        var refreshToken = jwtService.generateRefreshToken(optUser);
        revokeAllUserTokens(optUser);
        saveToken(optUser, token);
        return AuthResponse.builder()
                .accessToken(token)
                .refreshToken(refreshToken)
                .build();
    }

    private void saveToken(User user, String token){
        tokenRepository.save(
            new Token(token, TokenType.BEARER,false,false,user)
        );
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user);
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userName;
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userName = jwtService.extractUserName(refreshToken);
        if (userName != null) {
            User user = userRepository.findByUsername(userName).orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveToken(user, accessToken);
                var authResponse = AuthResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }
}
