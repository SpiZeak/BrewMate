package com.BrewMate.BrewMate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.BrewMate.BrewMate")
public class BrewMateApplication {

	public static void main(String[] args) {
		SpringApplication.run(BrewMateApplication.class, args);
	}
}
