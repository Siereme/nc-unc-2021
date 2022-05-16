package com.app;


import org.apache.log4j.BasicConfigurator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories({"com.app.repository"})
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class Main {

	public static void main(String[] args) throws Exception {
        BasicConfigurator.configure();
		SpringApplication.run(Main.class, args);
	}

}


