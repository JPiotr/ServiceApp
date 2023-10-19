package com.oblitus.serviceApp;

import com.oblitus.serviceApp.Modules.Admin.*;
import com.oblitus.serviceApp.Modules.Admin.DTOs.UserDTO;
import com.oblitus.serviceApp.Modules.Admin.DTOs.UserResponse;
import com.oblitus.serviceApp.Modules.ModuleRepository;
import com.oblitus.serviceApp.Modules.ModulesWrapper;
import com.oblitus.serviceApp.Modules.Service.ClientRepository;
import com.oblitus.serviceApp.Modules.Service.DTOs.ClientDTO;
import com.oblitus.serviceApp.Modules.Service.DTOs.CommentDTO;
import com.oblitus.serviceApp.Modules.Service.DTOs.TicketDTO;
import com.oblitus.serviceApp.Modules.Service.TicketPriority;
import com.oblitus.serviceApp.Modules.Service.TicketState;
import com.oblitus.serviceApp.Utils.StaticInfo;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

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
		UserRepository userRepository,
		ClientRepository clientRepository,
		ModulesWrapper modulesWrapper,
		PasswordEncoder encoder
		){return args -> {


		moduleRepository.saveAll(StaticInfo.Modules);
		ruleRepository.saveAll(StaticInfo.PredefinedRules);
		StaticInfo.SuperUser.setPassword(encoder.encode(UUID.randomUUID().toString()));
		userRepository.save(StaticInfo.SuperUser);
		modulesWrapper.adminModule.getAdminDAO().getUserDao().addRuleForUser(StaticInfo.SuperUser.getID(), ERule.ADMIN.toString());
		clientRepository.save(StaticInfo.Internal);

		//users
		List<UserResponse> users =
				List.of(
						modulesWrapper.adminModule.getAdminDAO().getUserDao()
								//John Doe
								.save(new UserDTO(
										null,
										"jdoe@domain.srvtrack",
										"JohnDoeRoot",
										"John",
										"Doe",
										"simplePass"
								)),
						modulesWrapper.adminModule.getAdminDAO().getUserDao()
								//John Necessary
								.save(new UserDTO(
										null,
										"jnecessary@domain.srvtrack",
										"JohnNecessaryClient",
										"John",
										"Necessary",
										"simplePass"
								)),
						modulesWrapper.adminModule.getAdminDAO().getUserDao()
								//Grzegorz Brzęczyszczykiewicz
								.save(new UserDTO(
										null,
										"gbrzeczyszczykiewicz@domain.srvtrack",
										"JohnDoeRoot",
										"Grzegorz",
										"Brzęczyszczykiewicz",
										"jakrozpetalemdrugawojneswiatowa"
								))
				);

		var jdoe = modulesWrapper.adminModule.getAdminDAO().getUserDao().addRuleForUser(users.get(0).id(), ERule.ADMIN.toString());
		var jnes = modulesWrapper.adminModule.getAdminDAO().getUserDao().addRuleForUser(users.get(1).id(), ERule.CLIENT.toString());
		var gbrz = modulesWrapper.adminModule.getAdminDAO().getUserDao().addRuleForUser(users.get(2).id(), ERule.SERVICE.toString());

		var client1 = modulesWrapper.serviceModule.getServiceDAO().getClientDao().save(new ClientDTO(null, "Client X", jdoe.id()));
		var client2 = modulesWrapper.serviceModule.getServiceDAO().getClientDao().save(new ClientDTO(null, "Client Y", jdoe.id()));

		var tickets = List.of(
				new TicketDTO(
						null,
						"Hello World",
						"First simple Ticket",
						client1.id(),
						gbrz.id(),
						TicketState.OPEN,
						TicketPriority.HIGH,
						jnes.id(),
						null
				),
				new TicketDTO(
						null,
						"Need Help - URGENT",
						"I don't know how to pronounce Your IT Guy name. Can You assign someone else?",
//						new ArrayList<CommentDTO>(),
						client2.id(),
						null,
						TicketState.NEW,
						TicketPriority.MEDIUM,
						jnes.id(),
						null
				)
		);

		modulesWrapper.serviceModule.getServiceDAO().getTicketDao().save(tickets.get(0));
		var ticketResponse = modulesWrapper.serviceModule.getServiceDAO().getTicketDao().save(tickets.get(1));

		CommentDTO comment = new CommentDTO(null, "We don't have anyone else :(", ticketResponse.id(), jdoe.id());
		CommentDTO comment2 = new CommentDTO(null, "How can I call him then?", ticketResponse.id(), jnes.id());
		CommentDTO comment3 = new CommentDTO(null, "Gregory?", ticketResponse.id(), jdoe.id());


		modulesWrapper.serviceModule.getServiceDAO().getCommentDao().save(comment);
		modulesWrapper.serviceModule.getServiceDAO().getCommentDao().save(comment2);
		modulesWrapper.serviceModule.getServiceDAO().getCommentDao().save(comment3);

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
