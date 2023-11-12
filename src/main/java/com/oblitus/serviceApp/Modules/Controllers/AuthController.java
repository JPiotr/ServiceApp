package com.oblitus.serviceApp.Modules.Controllers;

import com.oblitus.serviceApp.Common.Response;
import com.oblitus.serviceApp.Modules.Admin.AuthenticationService;
import com.oblitus.serviceApp.Modules.Admin.DTOs.LUserDTO;
import com.oblitus.serviceApp.Modules.Admin.DTOs.RUserDTO;
import com.oblitus.serviceApp.Modules.Admin.Exceptions.AccountAlreadyExistException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<Response> registerUser(@RequestBody @Validated RUserDTO userDTO) {
        try {
            return  ResponseEntity.ok(
                    Response.builder()
                            .timestamp(LocalDateTime.now())
                            .message("New User added to Database. And token generated.")
                            .data(Map.of("result", authenticationService.register(userDTO)))
                            .statusCode(HttpStatus.CREATED.value())
                            .status(HttpStatus.CREATED)
                            .build()
            );
        } catch (AccountAlreadyExistException e) {
            return  ResponseEntity.ok(
                    Response.builder()
                            .timestamp(LocalDateTime.now())
                            .message("Cannot register user.")
                            .data(Map.of("result", " "))
                            .statusCode(HttpStatus.NOT_ACCEPTABLE.value())
                            .reason("User with this username already exist")
                            .devMessage(e.getMessage())
                            .status(HttpStatus.NOT_ACCEPTABLE)
                            .build()
            );
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Response> login(@RequestBody @Validated LUserDTO userDTO){
        try {
            return  ResponseEntity.ok(
                    Response.builder()
                            .timestamp(LocalDateTime.now())
                            .message("Login approve")
                            .data(Map.of("token", authenticationService.login(userDTO)))
                            .statusCode(HttpStatus.OK.value())
                            .status(HttpStatus.OK)
                            .build()
            );
        } catch (BadCredentialsException e) {
            return  ResponseEntity.ok(
                    Response.builder()
                            .timestamp(LocalDateTime.now())
                            .message("Login denied.")
                            .data(Map.of("token", " "))
                            .statusCode(HttpStatus.FORBIDDEN.value())
                            .status(HttpStatus.FORBIDDEN)
                            .reason("Login or Password wrong!")
                            .build()
            );
        }
    }

    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        authenticationService.refreshToken(request, response);
    }

}
