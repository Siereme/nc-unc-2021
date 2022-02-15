package com.example.app;

import org.apache.log4j.BasicConfigurator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.text.SimpleDateFormat;

@SpringBootApplication
public class Main {
	public static void main(String[] args) {
/*		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword1 = passwordEncoder.encode("admin1");
		String hashedPassword2 = passwordEncoder.encode("user1");
		System.out.println(hashedPassword1);
		System.out.println(hashedPassword2);*/
		BasicConfigurator.configure();
		SpringApplication.run(Main.class, args);
	}
}
