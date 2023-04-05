package com.oblitus.serviceApp.Modules.Controllers;

import com.oblitus.serviceApp.Modules.Admin.Singletons.AdminModule;
import com.oblitus.serviceApp.Modules.Admin.User;
import com.oblitus.serviceApp.Modules.Admin.UserService;
import lombok.AllArgsConstructor;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("adminModule")
@AllArgsConstructor
public class AdminController {
    private static final AdminModule adminModule = AdminModule.getInstance();


}
