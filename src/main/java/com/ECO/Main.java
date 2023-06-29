package com.ECO;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.ECO:login_System.appuser")
@EntityScan("com.ECO:login_System.appuser")
public class Main {
	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}

}
