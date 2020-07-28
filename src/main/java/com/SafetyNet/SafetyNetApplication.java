package com.SafetyNet;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

import java.text.ParseException;

@SpringBootApplication
public class SafetyNetApplication {

	@Autowired
	private Environment env;
	@Bean
	public Data data() {
		String path = "src/main/resources/data.json";
		return new Data(path);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	public static void main(String[] args) {
		Logger logger = Logger.getLogger(SafetyNetApplication.class);
		BasicConfigurator.configure();
		new SpringApplicationBuilder(SafetyNetApplication.class).run();
		logger.info("This is a log from log4j.");
	}
}
