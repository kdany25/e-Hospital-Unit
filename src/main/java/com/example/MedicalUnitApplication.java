package com.example;

import com.opencsv.CSVWriter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import java.io.FileWriter;
import java.io.IOException;
import java.io.File;

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
	 * 
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		SpringApplication.run(MedicalUnitApplication.class, args);
		String workingDir = System.getProperty("user.dir");
		File file = new File(workingDir + "/medecine.csv");

		if (!file.exists()) {
			file.createNewFile();
		}
		try (CSVWriter writer = new CSVWriter(new FileWriter(file))) {
			String[] header = { "Name", "Price", "Expiration Date" };
			writer.writeNext(header);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
