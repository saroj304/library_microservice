package com.library.library_microservice;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Library Microservice REST API Documentation",
				description = "Comprehensive documentation for the Library Management Microservice.",
				version = "v1",
				contact = @Contact(
						name = "Saroj Khatiwada",
						email = "sarojkhatiwada1999@gmail.com"
				),
				license = @License(
						name = "GitHub Repository",
						url = "https://github.com/saroj304/microservice-bankAccount-management/tree/master"
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "Additional documentation for the Library Management Microservice.",
				url = "https://github.com/saroj304/microservice-bankAccount-management/tree/master"
		)
)
public class LibraryMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibraryMicroserviceApplication.class, args);
	}
}

