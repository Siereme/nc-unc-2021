package com.app;

import org.apache.log4j.BasicConfigurator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

// без этой настройки работать не будет, у энтитименеджера конфликт с хибером...(или спрингбут возмущается, я так и не понял)
@SpringBootApplication(exclude = HibernateJpaAutoConfiguration.class)
public class Main {
	public static void main(String[] args) {
        BasicConfigurator.configure();
		SpringApplication.run(Main.class, args);
	}
}
