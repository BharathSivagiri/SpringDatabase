package com.roadmap.SpringDatabase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan({"com.roadmap.services"})
@EntityScan("com.roadmap.entities")
@EnableJpaRepositories("com.roadmap.repositories")
public class MainApp {

	public static void main(String[] args)
	{
		SpringApplication.run(MainApp.class, args);
	}

}
