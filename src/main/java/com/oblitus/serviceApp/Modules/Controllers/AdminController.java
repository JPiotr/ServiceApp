package com.oblitus.serviceApp.Modules.Controllers;

import com.oblitus.serviceApp.Common.Response;
import com.oblitus.serviceApp.Modules.Admin.DTOs.UserDTO;
import com.oblitus.serviceApp.Modules.Admin.Responses.UserResponse;
import com.oblitus.serviceApp.Modules.Admin.ERule;
import com.oblitus.serviceApp.Modules.MappersWrapper;
import com.oblitus.serviceApp.Modules.ModulesWrapper;
import jakarta.annotation.Nullable;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("/adminModule")
@RequiredArgsConstructor
public class AdminController {
    private final MappersWrapper mappersWrapper;
    private final ModulesWrapper modulesWrapper;

    @GetMapping("/user/{id}")
    public ResponseEntity<Response> getUser(@PathVariable @Validated UUID id){
        try{
            UserResponse user = mappersWrapper.userMapper.apply(
                    modulesWrapper.adminModule.getAdminDAO().getUserService().get(new UserDTO(id))
            );
            return ResponseEntity.ok(
                    Response.builder()
                    .timestamp(LocalDateTime.now())
                    .message("User with ID = " + id + ".")
                    .data(Map.of("user", user))
                    .statusCode(HttpStatus.OK.value())
                    .status(HttpStatus.OK)
                    .build());
        }
        catch (EntityNotFoundException exception){
            return ResponseEntity.ok(
                    Response.builder()
                            .timestamp(LocalDateTime.now())
                            .message("User " + id + " not found.")
                            .data(Map.of("user", " "))
                            .statusCode(HttpStatus.NOT_FOUND.value())
                            .status(HttpStatus.NOT_FOUND)
                            .reason("There is no Entity with this ID!")
                            .devMessage(exception.getMessage())
                            .build());
        }
    }

    @PatchMapping("/user/state/{id}")
    public ResponseEntity<Response> changeEnabled(@PathVariable @Validated UUID id) {
        try{
            return ResponseEntity.ok(
                    Response.builder()
                            .timestamp(LocalDateTime.now())
                            .message("Changed user enabled")
                            .data(Map.of("user", mappersWrapper.userMapper.apply(
                                    modulesWrapper.adminModule.getAdminDAO().getUserService()
                                            .changeUserEnabled(new UserDTO(id))
                                            )
                                    )
                            )
                            .statusCode(HttpStatus.OK.value())
                            .status(HttpStatus.OK)
                            .build()
            );
        }
        catch (EntityNotFoundException exception){
            return ResponseEntity.ok(
                    Response.builder()
                            .timestamp(LocalDateTime.now())
                            .message("User " + id + " not found.")
                            .data(Map.of("user",  " "))
                            .statusCode(HttpStatus.NO_CONTENT.value())
                            .status(HttpStatus.NO_CONTENT)
                            .reason("There is no Entity with this ID!")
                            .devMessage(exception.getMessage())
                            .build()
            );
        }

    }

    @PostMapping("/users")
    public ResponseEntity<Response> addUser(@RequestBody @Validated UserDTO userDTO){
        return ResponseEntity.ok(
                Response.builder()
                        .timestamp(LocalDateTime.now())
                        .message("New User added to Database.")
                        .data(Map.of("user", mappersWrapper.userMapper.apply(
                                modulesWrapper.adminModule.getAdminDAO().getUserService().add(userDTO))
                                )
                        )
                        .statusCode(HttpStatus.CREATED.value())
                        .status(HttpStatus.CREATED)
                        .build()
        );
    }

    @GetMapping("/users")
    public ResponseEntity<Response> getUsers(@RequestParam @Nullable @Validated String ruleName){
        if(ruleName != null && Arrays.stream(ERule.values()).anyMatch(x-> Objects.equals(x.toString(), ruleName))){
            return ResponseEntity.ok(
                    Response.builder()
                            .timestamp(LocalDateTime.now())
                            .message("All users with rule "+ruleName)
                            .data(Map.of("users",modulesWrapper.adminModule.getAdminDAO().getUserService().getAll()
                                    .stream().map(mappersWrapper.userMapper)
                                    .filter(userResponse -> userResponse.getRules().stream()
                                            .anyMatch(ruleDTO -> Objects.equals(ruleDTO.name(), ruleName)))
                                    .toList()))
                            .statusCode(HttpStatus.OK.value())
                            .status(HttpStatus.OK)
                            .build()
            );
        }
        return ResponseEntity.ok(
                Response.builder()
                        .timestamp(LocalDateTime.now())
                        .message("All existing users.")
                        .data(Map.of("users",modulesWrapper.adminModule.getAdminDAO().getUserService().getAll()
                                .stream().map(mappersWrapper.userMapper).toList()))
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .build()
        );
    }

    @PutMapping("/user")
    public ResponseEntity<Response> updateOrCreateUser(@RequestBody @Validated UserDTO userDTO) {
        try {
            return ResponseEntity.ok(
                    Response.builder()
                            .timestamp(LocalDateTime.now())
                            .message("User updated.")
                            .data(Map.of("user", mappersWrapper.userMapper.apply(
                                    modulesWrapper.adminModule.getAdminDAO().getUserService().update(userDTO))
                                    )
                            )
                            .statusCode(HttpStatus.ACCEPTED.value())
                            .status(HttpStatus.ACCEPTED)
                            .build()
            );

        }
        catch(EntityNotFoundException e){
            return ResponseEntity.ok(
                    Response.builder()
                            .timestamp(LocalDateTime.now())
                            .message("New User added to Database.")
                            .data(Map.of("user", mappersWrapper.userMapper.apply(
                                    modulesWrapper.adminModule.getAdminDAO().getUserService().add(userDTO))
                                    )
                            )
                            .statusCode(HttpStatus.CREATED.value())
                            .status(HttpStatus.CREATED)
                            .build()
            );
        }
    }

    @DeleteMapping("/user")
    public ResponseEntity<Response> deleteUser(@RequestBody @Validated UserDTO userDTO){
        try{
            return ResponseEntity.ok(
                    Response.builder()
                            .timestamp(LocalDateTime.now())
                            .message("Try to drop User")
                            .data(Map.of("result", modulesWrapper.adminModule.getAdminDAO().getUserService()
                                    .delete(userDTO)
                                    )
                            )
                            .statusCode(HttpStatus.OK.value())
                            .status(HttpStatus.OK)
                            .build()
            );
        }
        catch (EntityNotFoundException e){
            return ResponseEntity.ok(
                    Response.builder()
                            .timestamp(LocalDateTime.now())
                            .message("User " + userDTO.id() + " not found.")
                            .data(Map.of("user", " "))
                            .statusCode(HttpStatus.NOT_FOUND.value())
                            .status(HttpStatus.NOT_FOUND)
                            .reason("There is no Entity with this ID!")
                            .build()
            );
        }

    }
}