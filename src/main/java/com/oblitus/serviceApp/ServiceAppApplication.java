package com.oblitus.serviceApp;

import com.oblitus.serviceApp.Common.Email.EmailService;
import com.oblitus.serviceApp.Modules.Admin.*;
import com.oblitus.serviceApp.Modules.Admin.Responses.RuleMapper;
import com.oblitus.serviceApp.Modules.Admin.DTOs.UserDTO;
import com.oblitus.serviceApp.Modules.Admin.Responses.UserResponse;
import com.oblitus.serviceApp.Modules.Admin.Responses.UserResponseMapper;
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
import java.util.stream.Stream;

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
		RuleMapper ruleMapper,
		UserResponseMapper userMapper,
		ModulesWrapper modulesWrapper,
		PasswordEncoder encoder,
		EmailService emailService
		){return args -> {
		if(!modulesWrapper.adminModule.getAdminDAO().getUserService().getAll().isEmpty()){
			return;
		}

		moduleRepository.saveAll(StaticInfo.Modules);
		ruleRepository.saveAll(StaticInfo.PredefinedRules);
		StaticInfo.SuperUser.setPassword(encoder.encode(StaticInfo.SuperUserPasswd));
		userRepository.save(StaticInfo.SuperUser);
		modulesWrapper.adminModule.getAdminDAO().getUserService().update(
				new UserDTO(
						StaticInfo.SuperUser.getUuid()
						,null
						,null
						,null
						,null
						,null
						,List.of(ruleMapper.apply(StaticInfo.PredefinedRules.get(0)))
						,null
						,false)
		);
		clientRepository.save(StaticInfo.Internal);

		//users
		List<UserResponse> users =
				Stream.of(
						modulesWrapper.adminModule.getAdminDAO().getUserService()
								//John Doe
								.add(new UserDTO(
										null,
										"jdoe@domain.srvtrack",
										"JohnDoeRoot",
										"John",
										"Doe",
										"simplePass",
										Stream.of(
												StaticInfo.PredefinedRules.get(1)
												,StaticInfo.PredefinedRules.get(0)).map(ruleMapper).toList(),
										null,false
								)),
						modulesWrapper.adminModule.getAdminDAO().getUserService()
								//John Necessary
								.add(new UserDTO(
										null,
										"jnecessary@domain.srvtrack",
										"JohnNecessaryClient",
										"John",
										"Necessary",
										"simplePass",
										Stream.of(StaticInfo.PredefinedRules.get(1)
												 ,StaticInfo.PredefinedRules.get(2)).map(ruleMapper).toList(),
										null,true
								)),
						modulesWrapper.adminModule.getAdminDAO().getUserService()
								//Grzegorz Brzęczyszczykiewicz
								.add(new UserDTO(
										null,
										"gbrzeczyszczykiewicz@domain.srvtrack",
										"Grzegorz",
										"Grzegorz",
										"Brzęczyszczykiewicz",
										"jakrozpetalemdrugawojneswiatowa",
										Stream.of(StaticInfo.PredefinedRules.get(1)
												,StaticInfo.PredefinedRules.get(3)).map(ruleMapper).toList(),
										null,true
								))
				).map(userMapper).toList();


		var client1 = modulesWrapper.serviceModule.getServiceDAO().getClientService()
				.add(new ClientDTO(null, "Client X", users.get(0).getUuid()));
		var client2 = modulesWrapper.serviceModule.getServiceDAO().getClientService()
				.add(new ClientDTO(null, "Client Y", users.get(0).getUuid()));

		var tickets = List.of(
				new TicketDTO(
						null,
						"Hello World",
						"First simple Ticket",
						client1.getUuid(),
						users.get(2).getUuid(),
						TicketState.OPEN,
						TicketPriority.HIGH,
						users.get(1).getUuid(),
						null,
						null,
						null
				),
				new TicketDTO(
						null,
						"Need Help - URGENT",
						"I don't know how to pronounce Your IT Guy name. Can You assign someone else?",
//						new ArrayList<CommentDTO>(),
						client2.getUuid(),
						users.get(0).getUuid(),
						TicketState.NEW,
						TicketPriority.MEDIUM,
						users.get(1).getUuid(),
						null,
						null,
						null
				)
		);

		modulesWrapper.serviceModule.getServiceDAO().getTicketService().add(tickets.get(0));
		var ticketResponse = modulesWrapper.serviceModule.getServiceDAO().getTicketService().add(tickets.get(1));

		CommentDTO comment = new CommentDTO(null, "We don't have anyone else :("
				, ticketResponse.getUuid(), users.get(0).getUuid());
		CommentDTO comment2 = new CommentDTO(null, "How can I call him then?"
				, ticketResponse.getUuid(), users.get(1).getUuid());
		CommentDTO comment3 = new CommentDTO(null, "Gregory?"
				, ticketResponse.getUuid(), users.get(0).getUuid());


		modulesWrapper.serviceModule.getServiceDAO().getCommentService().add(comment);
		modulesWrapper.serviceModule.getServiceDAO().getCommentService().add(comment2);
		modulesWrapper.serviceModule.getServiceDAO().getCommentService().add(comment3);

		//todo:enable this on deploy
//		emailService.sendEmail("srvctrack@gmail.com","Application Start","Application started successfully!");

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
