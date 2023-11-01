package com.oblitus.serviceApp.Modules.Controllers;

import com.oblitus.serviceApp.Common.Response;
import com.oblitus.serviceApp.Modules.Admin.AuthenticationService;
import com.oblitus.serviceApp.Modules.Admin.DTOs.LUserDTO;
import com.oblitus.serviceApp.Modules.Admin.DTOs.RUserDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PutMapping("/register")
    public ResponseEntity<Response> registerUser(@RequestBody @Validated RUserDTO userDTO) {
        return  ResponseEntity.ok(
                Response.builder()
                        .timestamp(LocalDateTime.now())
                        .message("New User putted to Database. And token generated.")
                        .data(Map.of("token", authenticationService.register(userDTO)))
                        .statusCode(HttpStatus.CREATED.value())
                        .status(HttpStatus.CREATED)
                        .build()
        );
    }

    @PostMapping("/login")
    public ResponseEntity<Response> login(@RequestBody @Validated LUserDTO userDTO){
        return  ResponseEntity.ok(
                Response.builder()
                        .timestamp(LocalDateTime.now())
                        .message("Login approve")
                        .data(Map.of("token", authenticationService.login(userDTO)))
                        .statusCode(HttpStatus.CREATED.value())
                        .status(HttpStatus.CREATED)
                        .build()
        );
    }

    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        authenticationService.refreshToken(request, response);
    }

    //todo:Do this by Profile controller, and apply to Administrator controller changing password
//    @PostMapping("/change-password")
//    public ResponseEntity<Response> changePassword(){
//
//    }

}
