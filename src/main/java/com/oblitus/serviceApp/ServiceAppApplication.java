package com.oblitus.serviceApp;

import com.oblitus.serviceApp.Modules.Admin.DTOs.RuleDTO;
import com.oblitus.serviceApp.Modules.Admin.DTOs.UserDTO;
import com.oblitus.serviceApp.Modules.Admin.ERule;
import com.oblitus.serviceApp.Modules.EModule;
import com.oblitus.serviceApp.Modules.Module;
import com.oblitus.serviceApp.Modules.ModuleRepository;
import com.oblitus.serviceApp.Modules.ModulesWrapper;
import com.oblitus.serviceApp.Security.DataCrypt.Crypt;
//import com.oblitus.serviceApp.Modules.Admin.Role;
//import com.oblitus.serviceApp.Modules.Admin.User;
//import com.oblitus.serviceApp.Modules.Admin.RoleService;
//import com.oblitus.serviceApp.Modules.Admin.UserService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SpringBootApplication
@AllArgsConstructor
public class ServiceAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceAppApplication.class, args);
	}

	@Bean
	CommandLineRunner dbinit(
		ModuleRepository moduleRepository,
		ModulesWrapper modulesWrapper
	){return args -> {
		Crypt crypt = new Crypt();
		List<Module> modules = List.of(
				new Module(EModule.ADMIN_MODULE.name()	 ,true ,EModule.ADMIN_MODULE		),
				new Module(EModule.BASE_MODULE.name()	 ,true ,EModule.BASE_MODULE		),
//				new Module(EModule.FINANCE_MODULE.name() ,false,EModule.FINANCE_MODULE	),
				new Module(EModule.PROJECTS_MODULE.name(),false,EModule.PROJECTS_MODULE	),
//				new Module(EModule.CRM_MODULE.name()	 ,false,EModule.CRM_MODULE		),
//				new Module(EModule.CASH_MODULE.name()	 ,false,EModule.CASH_MODULE		),
				new Module(EModule.SERVICE_MODULE.name() ,false,EModule.SERVICE_MODULE	)
		);

		moduleRepository.saveAll(modules);

		RuleDTO rootRole = new RuleDTO(
				UUID.randomUUID(),
				ERule.ADMIN.name(),
				List.of(modules.get(0),
						modules.get(1)
				)
		);

		rootRole = modulesWrapper.adminModule.getAdminDAO().getRuleDao().save(rootRole);

		UserDTO root = new UserDTO(
				UUID.randomUUID(),
				"root",
				"root",
				"root",
				" ",
				null,
				LocalDateTime.now().plusMonths(1L),
				LocalDateTime.now().plusMonths(1L),
				false,
				 false,
				true,
				"rootpass",
				List.of(
						rootRole
				)
		);

		modulesWrapper.adminModule.getAdminDAO().getUserDao().save(root);


	};}
}
