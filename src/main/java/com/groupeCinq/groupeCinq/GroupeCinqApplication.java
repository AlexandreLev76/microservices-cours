package com.groupeCinq.groupeCinq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@EnableJpaRepositories
public class GroupeCinqApplication {

	public static void main(String[] args) {
		SpringApplication.run(GroupeCinqApplication.class, args);
	}
}
