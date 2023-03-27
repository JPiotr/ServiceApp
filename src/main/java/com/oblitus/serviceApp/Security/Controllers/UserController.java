package com.oblitus.serviceApp.Security.Controllers;

import com.oblitus.serviceApp.Security.Entities.User;
import com.oblitus.serviceApp.Security.Services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller("adminModule/")
@AllArgsConstructor
public class UserController {
    public final UserService userService;

    @QueryMapping
    public Iterable<User> users(){
        return userService.getAllUsers();
    }

    @GetMapping("users")
    public Iterable<User> usersGet(){
        return userService.getAllUsers();
    }
}
