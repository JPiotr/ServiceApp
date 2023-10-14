package com.oblitus.serviceApp;

import com.oblitus.serviceApp.Modules.Admin.DTOs.RuleDTO;
import com.oblitus.serviceApp.Modules.Admin.DTOs.UserDTO;
import com.oblitus.serviceApp.Modules.Admin.ERule;
import com.oblitus.serviceApp.Modules.Admin.Rule;
import com.oblitus.serviceApp.Modules.Admin.RuleRepository;
import com.oblitus.serviceApp.Modules.EModule;
import com.oblitus.serviceApp.Modules.Module;
import com.oblitus.serviceApp.Modules.ModuleRepository;
import com.oblitus.serviceApp.Modules.ModulesWrapper;
import com.oblitus.serviceApp.Modules.Service.Comment;
import com.oblitus.serviceApp.Modules.Service.DTOs.ClientDTO;
import com.oblitus.serviceApp.Modules.Service.DTOs.CommentDTO;
import com.oblitus.serviceApp.Modules.Service.DTOs.TicketDTO;
import com.oblitus.serviceApp.Modules.Service.TicketPriority;
import com.oblitus.serviceApp.Modules.Service.TicketState;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.time.LocalDateTime;
import java.util.*;

@SpringBootApplication
@AllArgsConstructor
public class ServiceAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceAppApplication.class, args);
	}

	@Bean
	CommandLineRunner dbinit(
		ModuleRepository moduleRepository,
		RuleRepository ruleRepository,
		ModulesWrapper modulesWrapper
		){return args -> {
		Crypt crypt = new Crypt();
		List<Module> modules = List.of(
				new Module(EModule.ADMIN_MODULE.name()	 ,true ,EModule.ADMIN_MODULE		),
				new Module(EModule.BASE_MODULE.name()	 ,true ,EModule.BASE_MODULE		),
				new Module(EModule.PROJECTS_MODULE.name(),false,EModule.PROJECTS_MODULE	),
				new Module(EModule.SERVICE_MODULE.name() ,true,EModule.SERVICE_MODULE	)
		);

		moduleRepository.saveAll(modules);

		var rules = List.of(
				new RuleDTO(
						UUID.randomUUID(),
						ERule.ADMIN.name(),
						List.of(modules.get(0),
								modules.get(1)
						)),
				new RuleDTO(
						UUID.randomUUID(),
						ERule.USER.name(),
						List.of(modules.get(1))),
				new RuleDTO(
						UUID.randomUUID(),
						ERule.CLIENT.name(),
						List.of(modules.get(1),
								modules.get(3)))

		);

		for (var role : rules) {
			modulesWrapper.adminModule.getAdminDAO().getRuleDao().save(role);
		}


		UserDTO root = new UserDTO(
				UUID.randomUUID(),
				"root",
				"root",
				"root",
				" ",
				null,
				LocalDateTime.now().plusMonths(1L),
				LocalDateTime.now().plusMonths(1L),
				true,
				 false,
				false,
				"rootpass",
				List.of(
						modulesWrapper.adminModule.getAdminDAO()
								.getRuleDao().getAll()
								.stream().filter(x-> Objects.equals(x.name(), ERule.ADMIN.toString()))
								.findFirst()
								.get()
				)
		);

		root = modulesWrapper.adminModule.getAdminDAO().getUserDao().save(root);

		ClientDTO client = new ClientDTO(UUID.randomUUID(), "Client X");

		client = modulesWrapper.serviceModule.getServiceDAO().getClientDao().save(client);

		var ticket = new TicketDTO(
				null,
				"Ticket1",
				"Mocking ticket from DB",
//						new ArrayList<CommentDTO>(),
				client,
				root.id(),
				TicketState.DONE,
				TicketPriority.HIGH
		);

		var tickets = List.of(
				new TicketDTO(
						null,
						"Ticket2",
						"Mocking ticket from DB2",
//						List.of(
//								modulesWrapper.serviceModule.getServiceDAO().getCommentDao().get(comment.id()).get()
//						),
						client,
						root.id(),
						TicketState.OPEN,
						TicketPriority.HIGH
				),
				new TicketDTO(
						null,
						"Ticket3",
						"Mocking ticket from DB3",
//						new ArrayList<CommentDTO>(),
						client,
						null,
						TicketState.NEW,
						TicketPriority.MEDIUM
				)
		);

		for (var ticket2:tickets) {
			modulesWrapper.serviceModule.getServiceDAO().getTicketDao().save(ticket2);
		}
		ticket = modulesWrapper.serviceModule.getServiceDAO().getTicketDao().save(ticket);

		CommentDTO comment = new CommentDTO(null, "Komentarz z backendu", ticket.id(), root.id());


		comment = modulesWrapper.serviceModule.getServiceDAO().getCommentDao().save(comment);

	};}

	@Bean
	public CorsFilter corsFilter(){
		UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.setAllowCredentials(true);
		corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
		corsConfiguration.setAllowedHeaders(Arrays.asList("Origin", "Access-Control-Allow-Origin", "Content-Type",
				"Accept", "Jwt-Token", "Authorization", "Origin, Accept", "X-Request-With", "Access-Control-Request-Method",
				"Access-Control-Request-Headers"));
		corsConfiguration.setExposedHeaders(Arrays.asList("Origin", "Content-Type", "Accept", "Jwt-Token", "Authorization",
				"Access-Control-Allow-Origin", "Access-Control-Allow-Origin", "Access-Control-Allow-Credential", "Filename"));
		corsConfiguration.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE","OPTIONS"));
		urlBasedCorsConfigurationSource.registerCorsConfiguration("/**",corsConfiguration);
		return new CorsFilter(urlBasedCorsConfigurationSource);
	}
}
