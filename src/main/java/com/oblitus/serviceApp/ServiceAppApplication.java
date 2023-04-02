package com.oblitus.serviceApp;

import com.oblitus.serviceApp.Security.Entities.Role;
import com.oblitus.serviceApp.Security.Entities.User;
import com.oblitus.serviceApp.Security.Repos.UserRepository;
import lombok.AllArgsConstructor;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.tool.schema.TargetType;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.EnumSet;

@SpringBootApplication
@AllArgsConstructor
public class ServiceAppApplication {

	public static void main(String[] args) {
		//todo: hibernate
//		SpringApplication.run(ServiceAppApplication.class, args);
//		MetadataSources metadataSources = new MetadataSources();
//		metadataSources.addAnnotatedClass(Role.class);
//		metadataSources.addAnnotatedClass(User.class);
//		Metadata metadata = metadataSources.buildMetadata();
//
//		SchemaExport schemaExport = new SchemaExport();
//		schemaExport.setFormat(true);
//		schemaExport.setOutputFile("create.sql");
//		schemaExport.createOnly(EnumSet.of(TargetType.SCRIPT), metadata);
	}

}
