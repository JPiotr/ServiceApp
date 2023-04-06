package com.oblitus.serviceApp.Modules.Controllers;

import com.oblitus.serviceApp.Common.Response;
import com.oblitus.serviceApp.Modules.Admin.DTOs.UserDTO;
import com.oblitus.serviceApp.Modules.ModulesWrapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
        //todo: obsługa UserDTO z null-em
        return ResponseEntity.ok(
                Response.builder()
                        .timestamp(LocalDateTime.now())
                        .massage("Getting specyfic user with ID = "+ id)
                        .data(Map.of("user",ModulesWrapper.adminModule.getAdminDAO().getUserDao().get(id)))
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .build()
        );
    }

    @GetMapping("/users")
    public List<UserDTO> getUsers(){
        return ModulesWrapper.adminModule.getAdminDAO().getUserDao().getAll();
    }

    @PutMapping("/users")
    public UserDTO putUser(@RequestBody @Validated UserDTO userDTO){
        return ModulesWrapper.adminModule.getAdminDAO().getUserDao().save(userDTO);
    }

}
