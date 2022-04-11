package com.waracle.cake.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.waracle.cake.entity.Cake;
import com.waracle.cake.repo.CakeRepository;

@Configuration
@EntityScan("com.waracle.cake.entity")
@EnableJpaRepositories(basePackages = "com.waracle.cake.repo")
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
@Profile("!test")
public class DbConfig {
	
	Logger log = LoggerFactory.getLogger(DbConfig.class);

	@Bean
	public AuditorAware<String> auditorProvider() {
		return new AuditorAwareImpl();
	}

	@Bean
	public CommandLineRunner initDatabase(CakeRepository repository) {
		if (repository.findAll().size() < 1) {
			return args -> {
				try {
					// insert initial records into DB
					repository.save(new Cake("Chocolate Cake", "With Egg"));
					repository.save(new Cake("Marshmallow cake", "Eagless"));
					repository.save(new Cake("Chocoship cookie cake", "Eagless"));
					repository.save(new Cake("Pinata cake", "With Egg"));
					repository.save(new Cake("Pineapple cake", "Eagless"));
					repository.save(new Cake("Rose cake", "With Egg"));
					repository.save(new Cake("Orange cake", "Eagless"));
					log.info("Records created in DB at start time");
				} catch (Exception e) {
					log.error("Error in initiating records into database.");
				}
			};
		} else
			return args -> {
				log.info("Records already initiated ");
			};
	}
}
