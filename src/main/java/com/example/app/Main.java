package com.example.app;

import org.apache.log4j.BasicConfigurator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class Main {
	public static void main(String[] args) {
		BasicConfigurator.configure();
		SpringApplication.run(Main.class, args);
	}
}
