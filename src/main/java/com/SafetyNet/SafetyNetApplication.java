package com.SafetyNet;

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
	public Data data() throws ParseException {
		String path = "src/main/resources/data.json";
		return new Data(path);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	public static void main(String[] args) {
		new SpringApplicationBuilder(SafetyNetApplication.class).run();
	}
}
