package com.oblitus.serviceApp.Modules.Controllers;

import com.oblitus.serviceApp.Common.Response;
import com.oblitus.serviceApp.Modules.Admin.DAOs.UserDAO;
import com.oblitus.serviceApp.Modules.Admin.DTOs.UserDTO;
import com.oblitus.serviceApp.Modules.ModulesWrapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountLockedException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/adminModule")
@AllArgsConstructor
public class AdminController {
    private static final ModulesWrapper modulesWrapper = ModulesWrapper.getInstance();

    @GetMapping("/users/user")
    public ResponseEntity<Response> getUser(@RequestBody @Validated UUID id){
        Optional<UserDTO> opt = ModulesWrapper.adminModule.getAdminDAO().getUserDao().get(id);
        if(opt.isEmpty()){
            return ResponseEntity.ok(
                    Response.builder()
                            .timestamp(LocalDateTime.now())
                            .massage("User " + id + " not found.")
                            .data(Map.of("user",null))
                            .statusCode(HttpStatus.NOT_FOUND.value())
                            .status(HttpStatus.NOT_FOUND)
                            .reason("There is no Entity with this ID!")
                            .build()
            );
        }
        return ResponseEntity.ok(
                Response.builder()
                        .timestamp(LocalDateTime.now())
                        .massage("User with ID = " + id + ".")
                        .data(Map.of("user",opt.get()))
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .build()
        );
    }

    @GetMapping("/users")
    public ResponseEntity<Response> getUsers(){
        return ResponseEntity.ok(
                Response.builder()
                        .timestamp(LocalDateTime.now())
                        .massage("All existing users.")
                        .data(Map.of("users",ModulesWrapper.adminModule.getAdminDAO().getUserDao().getAll()))
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .build()
        );
    }

    @PutMapping("/users")
    public ResponseEntity<Response> putUser(@RequestBody @Validated UserDTO userDTO){
        return ResponseEntity.ok(
                Response.builder()
                        .timestamp(LocalDateTime.now())
                        .massage("New User putted to Database.")
                        .data(Map.of("user",ModulesWrapper.adminModule.getAdminDAO().getUserDao().save(userDTO)))
                        .statusCode(HttpStatus.CREATED.value())
                        .status(HttpStatus.CREATED)
                        .build()
        );
    }

    @PostMapping("/user")
    public ResponseEntity<Response> updateUser(@RequestBody @Validated UserDTO userDTO) throws AccountLockedException {
        try {
            return ResponseEntity.ok(
                    Response.builder()
                            .timestamp(LocalDateTime.now())
                            .massage("User updated.")
                            .data(Map.of("user", ModulesWrapper.adminModule.getAdminDAO().getUserDao().update(userDTO)))
                            .statusCode(HttpStatus.CREATED.value())
                            .status(HttpStatus.CREATED)
                            .build()
            );
        }
        catch (AccountLockedException e){
            return ResponseEntity.ok(
                    Response.builder()
                            .timestamp(LocalDateTime.now())
                            .massage(e.getMessage())
                            .data(Map.of("user",null))
                            .statusCode(HttpStatus.LOCKED.value())
                            .status(HttpStatus.LOCKED)
                            .build()
            );
        }
    }

    @DeleteMapping("/user")
    public ResponseEntity<Response> deleteUser(@RequestBody @Validated UserDTO userDTO){
        return ResponseEntity.ok(
          Response.builder()
                  .timestamp(LocalDateTime.now())
                  .massage("Try to drop User")
                  .data(Map.of("result", ModulesWrapper.adminModule.getAdminDAO().getUserDao().delete(userDTO)))
                  .statusCode(HttpStatus.OK.value())
                  .status(HttpStatus.OK)
                  .build()
        );
    }
}