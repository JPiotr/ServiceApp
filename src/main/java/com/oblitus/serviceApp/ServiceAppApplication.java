package com.oblitus.serviceApp;

import com.oblitus.serviceApp.Security.EModule;
import com.oblitus.serviceApp.Security.Entities.Role;
import com.oblitus.serviceApp.Security.Entities.User;
import com.oblitus.serviceApp.Security.Services.RoleService;
import com.oblitus.serviceApp.Security.Services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;

@SpringBootApplication
@AllArgsConstructor
public class ServiceAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceAppApplication.class, args);
	}

	@Bean
	CommandLineRunner dbinit(
		RoleService roleService,
		UserService userService
	){return args -> {
		ArrayList<EModule> modules = new ArrayList<>();
		modules.add(EModule.ADMIN_MODULE);

		Role role = new Role("Root",modules);

		ArrayList<Role> roles = new ArrayList<>();
		roles.add(role);

		User user = new User("Root","root@st.iai",roles,"root");

		roleService.addRole(role);
		userService.addUser(user);

	};}
}
