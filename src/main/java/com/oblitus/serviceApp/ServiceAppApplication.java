package com.oblitus.serviceApp;

import com.oblitus.serviceApp.Modules.Admin.AuthenticationService;
import com.oblitus.serviceApp.Modules.Admin.DTOs.RUserDTO;
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
				new Module(EModule.ADMIN_MODULE.toString()	 ,true ,EModule.ADMIN_MODULE		),
				new Module(EModule.BASE_MODULE.toString()	 ,true ,EModule.BASE_MODULE		),
				new Module(EModule.PROJECTS_MODULE.toString(),false,EModule.PROJECTS_MODULE	),
				new Module(EModule.SERVICE_MODULE.toString() ,true,EModule.SERVICE_MODULE	)
		);

		moduleRepository.saveAll(modules);

		var rules = List.of(
				new RuleDTO(
						UUID.randomUUID(),
						ERule.ADMIN.toString(),
						List.of(modules.get(0),
								modules.get(1)
						)),
				new RuleDTO(
						UUID.randomUUID(),
						ERule.USER.toString(),
						List.of(modules.get(1))),
				new RuleDTO(
						UUID.randomUUID(),
						ERule.CLIENT.toString(),
						List.of(modules.get(1),
								modules.get(3)))

		);

		for (var role : rules) {
			modulesWrapper.adminModule.getAdminDAO().getRuleDao().save(role);
		}

		var usrResponse = modulesWrapper.adminModule.getAdminDAO().getUserDao()
				.save(new UserDTO(
						null,
						"jdoe@domain.srvtrack",
						"JohnDoeRoot",
						"John",
						"Doe",
						"rootpass"
						));
		usrResponse = modulesWrapper.adminModule.getAdminDAO().getUserDao().addRuleForUser(usrResponse.id(), ERule.ADMIN.toString());
		usrResponse = modulesWrapper.adminModule.getAdminDAO().getUserDao().addRuleForUser(usrResponse.id(), ERule.USER.toString());

		var clientResponse = modulesWrapper.serviceModule.getServiceDAO().getClientDao().save(new ClientDTO(UUID.randomUUID(), "Client X"));

		var ticket = new TicketDTO(
				null,
				"Ticket1",
				"Mocking ticket from DB",
				clientResponse.id(),
				usrResponse.id(),
				TicketState.DONE,
				TicketPriority.HIGH
		);

		var tickets = List.of(
				new TicketDTO(
						null,
						"Ticket2",
						"Mocking ticket from DB2",
						clientResponse.id(),
						usrResponse.id(),
						TicketState.OPEN,
						TicketPriority.HIGH
				),
				new TicketDTO(
						null,
						"Ticket3",
						"Mocking ticket from DB3",
//						new ArrayList<CommentDTO>(),
						clientResponse.id(),
						usrResponse.id(),
						TicketState.NEW,
						TicketPriority.MEDIUM
				)
		);

		for (var ticket2:tickets) {
			modulesWrapper.serviceModule.getServiceDAO().getTicketDao().save(ticket2);
		}
		var ticketResponse = modulesWrapper.serviceModule.getServiceDAO().getTicketDao().save(ticket);

		CommentDTO comment = new CommentDTO(null, "Komentarz z backendu", ticketResponse.id(), usrResponse.id());
		CommentDTO comment2 = new CommentDTO(null, "Komentarz z backendu2", ticketResponse.id(), usrResponse.id());


		modulesWrapper.serviceModule.getServiceDAO().getCommentDao().save(comment);
		modulesWrapper.serviceModule.getServiceDAO().getCommentDao().save(comment2);

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
