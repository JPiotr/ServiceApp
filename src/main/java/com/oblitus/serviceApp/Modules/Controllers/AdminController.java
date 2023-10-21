package com.oblitus.serviceApp.Modules.Controllers;

import com.oblitus.serviceApp.Common.Response;
import com.oblitus.serviceApp.Modules.Admin.DTOs.UserDTO;
import com.oblitus.serviceApp.Modules.Admin.DTOs.UserResponse;
import com.oblitus.serviceApp.Modules.Admin.ERule;
import com.oblitus.serviceApp.Modules.ModulesWrapper;
import jakarta.annotation.Nullable;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountLockedException;
import javax.security.auth.login.AccountNotFoundException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("/adminModule")
@RequiredArgsConstructor
public class AdminController {
    private final ModulesWrapper modulesWrapper;

    @GetMapping("/user/{id}")
    public ResponseEntity<Response> getUser(@PathVariable @Validated UUID id){
        try{
            UserResponse user = modulesWrapper.adminModule.getAdminDAO().getUserDao().get(id);
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
                            .data(Map.of("user", null))
                            .statusCode(HttpStatus.NOT_FOUND.value())
                            .status(HttpStatus.NOT_FOUND)
                            .reason("There is no Entity with this ID!")
                            .build());
        }
    }

    @PostMapping("/user/{id}")
    public ResponseEntity<Response> ruleToUser(@PathVariable @Validated UUID id,
                                               @RequestParam @Validated String ruleName,
                                               @RequestParam @Validated boolean operation) {
        try{
            if(operation){
                return ResponseEntity.ok(
                        Response.builder()
                                .timestamp(LocalDateTime.now())
                                .message("Rule added to User")
                                .data(Map.of("user", modulesWrapper.adminModule.getAdminDAO().getUserDao().addRuleForUser(id,ruleName)))
                                .statusCode(HttpStatus.OK.value())
                                .status(HttpStatus.OK)
                                .build()
                );
            }
            return ResponseEntity.ok(
                    Response.builder()
                            .timestamp(LocalDateTime.now())
                            .message("Rule disconnected from User")
                            .data(Map.of("user", modulesWrapper.adminModule.getAdminDAO().getUserDao().disconnectRuleFromUser(id,ruleName)))
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
                            .data(Map.of("user", null))
                            .statusCode(HttpStatus.NOT_FOUND.value())
                            .status(HttpStatus.NOT_FOUND)
                            .reason("There is no Entity with this ID!")
                            .build()
            );
        }

    }

    @PostMapping("/user/state/{id}")
    public ResponseEntity<Response> changeEnabled(@PathVariable @Validated UUID id) {
        try{
            return ResponseEntity.ok(
                    Response.builder()
                            .timestamp(LocalDateTime.now())
                            .message("Changed user enabled")
                            .data(Map.of("user", modulesWrapper.adminModule.getAdminDAO().getUserDao().changeEnabled(id)))
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
                            .data(Map.of("user", null))
                            .statusCode(HttpStatus.NOT_FOUND.value())
                            .status(HttpStatus.NOT_FOUND)
                            .reason("There is no Entity with this ID!")
                            .build()
            );
        }

    }

    @PutMapping("/users")
    public ResponseEntity<Response> putUser(@RequestBody @Validated UserDTO userDTO){
        return ResponseEntity.ok(
                Response.builder()
                        .timestamp(LocalDateTime.now())
                        .message("New User putted to Database.")
                        .data(Map.of("user",modulesWrapper.adminModule.getAdminDAO().getUserDao().save(userDTO)))
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
                            .data(Map.of("users",modulesWrapper.adminModule.getAdminDAO().getUserDao().getAll()
                                    .stream().filter(userResponse -> userResponse.rules().stream()
                                            .anyMatch(ruleDTO -> Objects.equals(ruleDTO.name(), ruleName)))))
                            .statusCode(HttpStatus.OK.value())
                            .status(HttpStatus.OK)
                            .build()
            );
        }
        return ResponseEntity.ok(
                Response.builder()
                        .timestamp(LocalDateTime.now())
                        .message("All existing users.")
                        .data(Map.of("users",modulesWrapper.adminModule.getAdminDAO().getUserDao().getAll()))
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .build()
        );
    }

    @PostMapping("/user")
    public ResponseEntity<Response> updateUser(@RequestBody @Validated UserDTO userDTO) {
        try {
            return ResponseEntity.ok(
                    Response.builder()
                            .timestamp(LocalDateTime.now())
                            .message("User updated.")
                            .data(Map.of("user", modulesWrapper.adminModule.getAdminDAO().getUserDao().update(userDTO)))
                            .statusCode(HttpStatus.CREATED.value())
                            .status(HttpStatus.CREATED)
                            .build()
            );
        }
        catch (AccountLockedException e){
            return ResponseEntity.ok(
                    Response.builder()
                            .timestamp(LocalDateTime.now())
                            .message(e.getMessage())
                            .data(Map.of("user",null))
                            .statusCode(HttpStatus.LOCKED.value())
                            .status(HttpStatus.LOCKED)
                            .build()
            );
        }
        catch(EntityNotFoundException e){
            return ResponseEntity.ok(
                Response.builder()
                        .timestamp(LocalDateTime.now())
                        .message("User " + userDTO.id() + " not found.")
                        .data(Map.of("user", null))
                        .statusCode(HttpStatus.NOT_FOUND.value())
                        .status(HttpStatus.NOT_FOUND)
                        .reason("There is no Entity with this ID!")
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
                            .data(Map.of("result", modulesWrapper.adminModule.getAdminDAO().getUserDao().delete(userDTO)))
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
                            .data(Map.of("user", null))
                            .statusCode(HttpStatus.NOT_FOUND.value())
                            .status(HttpStatus.NOT_FOUND)
                            .reason("There is no Entity with this ID!")
                            .build()
            );
        }

    }
}