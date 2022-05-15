package com.app;


import com.app.model.actor.Actor;
import com.app.repository.actor.ActorRepository;
import org.apache.log4j.BasicConfigurator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@EnableMongoRepositories({"com.app.repository"})
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class Main implements CommandLineRunner {

	public static void main(String[] args) throws Exception {
        BasicConfigurator.configure();
		SpringApplication.run(Main.class, args);
	}


	@Autowired
	ActorRepository repository;
	@Transactional
	@Override
	public void run(String... args) throws Exception {
		Actor actor = new Actor();
		actor.setId(1);
		actor.setName("actor2");
		actor.setYear("90");
		repository.save(actor);

		System.out.println("________________________________________________");
		List<Actor> actors = repository.findAll();
		System.out.println(actors);
	}
}


