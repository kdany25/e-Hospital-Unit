package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * Initializes the hospital management application.
 */
@SpringBootApplication
public class MedicalUnitApplication extends SpringBootServletInitializer {

	/**
	 * Configures the application.
	 */
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(MedicalUnitApplication.class);
	}

	/**
	 * Starts the application.
	 */
	public static void main(String[] args) {
		SpringApplication.run(MedicalUnitApplication.class, args);
	}

}
