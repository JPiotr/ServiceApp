package com.oblitus.serviceApp.Modules.Controllers;

import com.oblitus.serviceApp.Common.Response;
import com.oblitus.serviceApp.Modules.Admin.DTOs.LUserDTO;
import com.oblitus.serviceApp.Modules.Admin.DTOs.RUserDTO;
import com.oblitus.serviceApp.Modules.Admin.DTOs.UserDTO;
import com.oblitus.serviceApp.Modules.Admin.ERule;
import com.oblitus.serviceApp.Modules.ModulesWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class BasicController {
    private final ModulesWrapper modulesWrapper;

    @PutMapping("/register")
    public ResponseEntity<Response> registerUser(@RequestBody @Validated RUserDTO userDTO){
        UserDTO user = new UserDTO(userDTO.userName(),
                        userDTO.name(),
                        userDTO.surname(),
                        userDTO.email(),
                        userDTO.password(),
                        modulesWrapper.adminModule.getAdminDAO().getRuleDao().getAll()
                                .stream().filter(
                                ruleDTO -> ruleDTO.name() == ERule.USER.toString()
                        ).collect(Collectors.toList())
                );
        return  ResponseEntity.ok(
                Response.builder()
                        .timestamp(LocalDateTime.now())
                        .message("New User putted to Database.")
                        .data(Map.of("user",modulesWrapper.adminModule.getAdminDAO().getUserDao().save(user)))
                        .statusCode(HttpStatus.CREATED.value())
                        .status(HttpStatus.CREATED)
                        .build()
        );
    }

    @PostMapping("/login")
    public ResponseEntity<Response> login(@RequestBody @Validated LUserDTO userDTO){

        return ResponseEntity.ok().build();
    }

    @PostMapping("/logout")
    public ResponseEntity<Response> logout(@RequestBody @Validated UUID userId){

        return ResponseEntity.ok().build();
    }


}
