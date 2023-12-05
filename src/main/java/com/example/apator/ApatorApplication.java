package com.example.apator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.scheduling.annotation.EnableScheduling;


@EnableScheduling
@SpringBootApplication
@EntityScan("com.example.apator.model")
public class ApatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApatorApplication.class, args);
	}

}
