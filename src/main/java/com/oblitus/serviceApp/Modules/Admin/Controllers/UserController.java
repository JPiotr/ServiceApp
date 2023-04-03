package com.oblitus.serviceApp.Modules.Admin.Controllers;

import com.oblitus.serviceApp.Modules.Admin.Entities.User;
import com.oblitus.serviceApp.Modules.Admin.Services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("adminModule")
@AllArgsConstructor
public class UserController {
    public final UserService userService;

    @QueryMapping
    public Iterable<User> users(){
        return userService.getAllUsers();
    }

    @GetMapping(value = "users")
    public Iterable<User> usersGet(){
        return userService.getAllUsers();
    }

}
