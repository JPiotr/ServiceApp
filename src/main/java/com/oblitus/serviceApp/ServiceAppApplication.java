package com.oblitus.serviceApp;

import com.oblitus.serviceApp.Modules.Admin.ERule;
import com.oblitus.serviceApp.Security.DataCrypt.Crypt;
import com.oblitus.serviceApp.Modules.Admin.EModule;
//import com.oblitus.serviceApp.Modules.Admin.Role;
//import com.oblitus.serviceApp.Modules.Admin.User;
//import com.oblitus.serviceApp.Modules.Admin.RoleService;
//import com.oblitus.serviceApp.Modules.Admin.UserService;
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
//		RoleService roleService,
//		UserService userService
	){return args -> {
		Crypt crypt = new Crypt();
//		ArrayList<EModule> modules = new ArrayList<>();
//		modules.add(EModule.ADMIN_MODULE);
//
//		Role role = new Role(ERule.ADMIN,modules);
//
//		ArrayList<Role> roles = new ArrayList<>();
//		roles.add(role);
//
//		User user = userService.addUser("Root","root@st.iai", roles,"root");
//
//		roleService.addRole(role);

	};}
}
